/**
 * Nebula Dark Theme JavaScript
 * Modern dark theme with vibrant nebula colors
 */

(function() {
  'use strict';

  // Theme configuration
  const config = {
    scrollThreshold: 100,
    animationDuration: 300,
    lazyLoadOffset: 50
  };

  // Utility functions
  const utils = {
    debounce: function(func, wait) {
      let timeout;
      return function executedFunction(...args) {
        const later = () => {
          clearTimeout(timeout);
          func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
      };
    },

    throttle: function(func, limit) {
      let inThrottle;
      return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
          func.apply(context, args);
          inThrottle = true;
          setTimeout(() => inThrottle = false, limit);
        }
      };
    },

    isElementInViewport: function(el) {
      const rect = el.getBoundingClientRect();
      return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
      );
    }
  };

  // Navbar functionality
  const navbar = {
    init: function() {
      this.navbar = document.querySelector('.navbar');
      if (!this.navbar) return;

      this.setupScrollEffect();
      this.setupMobileMenu();
    },

    setupScrollEffect: function() {
      let lastScrollTop = 0;
      
      window.addEventListener('scroll', utils.throttle(() => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        
        if (scrollTop > config.scrollThreshold) {
          this.navbar.classList.add('scrolled');
        } else {
          this.navbar.classList.remove('scrolled');
        }

        // Hide/show navbar on scroll
        if (scrollTop > lastScrollTop && scrollTop > config.scrollThreshold) {
          this.navbar.classList.add('navbar-hidden');
        } else {
          this.navbar.classList.remove('navbar-hidden');
        }
        
        lastScrollTop = scrollTop;
      }, 100));
    },

    setupMobileMenu: function() {
      const toggle = document.querySelector('.nav-toggle');
      const overlay = document.getElementById('nav-overlay');
      
      if (toggle && overlay) {
        toggle.addEventListener('click', () => {
          overlay.classList.add('active');
          document.body.style.overflow = 'hidden';
        });

        const closeBtn = overlay.querySelector('.nav-overlay-close');
        if (closeBtn) {
          closeBtn.addEventListener('click', () => {
            overlay.classList.remove('active');
            document.body.style.overflow = '';
          });
        }

        overlay.addEventListener('click', (e) => {
          if (e.target === overlay) {
            overlay.classList.remove('active');
            document.body.style.overflow = '';
          }
        });
      }
    }
  };

  // Animation on scroll
  const animations = {
    init: function() {
      this.animatedElements = document.querySelectorAll('.fade-in, .slide-up, .scale-in');
      if (this.animatedElements.length > 0) {
        this.setupObserver();
      }
    },

    setupObserver: function() {
      const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
      };

      const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('animated');
            observer.unobserve(entry.target);
          }
        });
      }, observerOptions);

      this.animatedElements.forEach(el => {
        observer.observe(el);
      });
    }
  };

  // Lazy loading for images
  const lazyLoader = {
    init: function() {
      this.images = document.querySelectorAll('img[data-src]');
      if (this.images.length > 0) {
        this.setupLazyLoading();
      }
    },

    setupLazyLoading: function() {
      const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            const img = entry.target;
            img.src = img.dataset.src;
            img.classList.remove('lazy');
            img.classList.add('loaded');
            observer.unobserve(img);
          }
        });
      }, {
        rootMargin: `${config.lazyLoadOffset}px`
      });

      this.images.forEach(img => {
        imageObserver.observe(img);
      });
    }
  };

  // Search functionality
  const search = {
    init: function() {
      this.searchInput = document.querySelector('.search-input');
      this.searchResults = document.querySelector('.search-results');
      
      if (this.searchInput && this.searchResults) {
        this.setupSearch();
      }
    },

    setupSearch: function() {
      let searchData = [];

      // Load search data
      fetch('/search.json')
        .then(response => response.json())
        .then(data => {
          searchData = data;
        })
        .catch(error => console.error('Error loading search data:', error));

      this.searchInput.addEventListener('input', utils.debounce((e) => {
        const query = e.target.value.toLowerCase().trim();
        
        if (query.length < 2) {
          this.searchResults.innerHTML = '';
          this.searchResults.classList.remove('active');
          return;
        }

        const results = searchData.filter(item => 
          item.title.toLowerCase().includes(query) ||
          item.content.toLowerCase().includes(query) ||
          (item.tags && item.tags.some(tag => tag.toLowerCase().includes(query)))
        ).slice(0, 5);

        this.displayResults(results, query);
      }, 300));
    },

    displayResults: function(results, query) {
      if (results.length === 0) {
        this.searchResults.innerHTML = '<div class="search-no-results">没有找到相关文章</div>';
      } else {
        this.searchResults.innerHTML = results.map(item => `
          <div class="search-result">
            <a href="${item.url}">
              <h4>${this.highlightMatch(item.title, query)}</h4>
              <p>${this.highlightMatch(item.excerpt || item.content.substring(0, 100), query)}...</p>
            </a>
          </div>
        `).join('');
      }
      
      this.searchResults.classList.add('active');
    },

    highlightMatch: function(text, query) {
      const regex = new RegExp(`(${query})`, 'gi');
      return text.replace(regex, '<mark>$1</mark>');
    }
  };

  // Back to top button
  const backToTop = {
    init: function() {
      this.button = document.createElement('button');
      this.button.className = 'back-to-top';
      this.button.innerHTML = '↑';
      this.button.setAttribute('aria-label', 'Back to top');
      document.body.appendChild(this.button);

      this.setupEventListeners();
    },

    setupEventListeners: function() {
      window.addEventListener('scroll', utils.throttle(() => {
        if (window.pageYOffset > 300) {
          this.button.classList.add('visible');
        } else {
          this.button.classList.remove('visible');
        }
      }, 100));

      this.button.addEventListener('click', () => {
        window.scrollTo({
          top: 0,
          behavior: 'smooth'
        });
      });
    }
  };

  // Theme toggle
  const themeToggle = {
    init: function() {
      this.toggle = document.querySelector('.theme-toggle');
      if (!this.toggle) return;

      this.setupThemeToggle();
      this.loadTheme();
    },

    setupThemeToggle: function() {
      this.toggle.addEventListener('click', () => {
        const currentTheme = document.documentElement.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        
        document.documentElement.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        
        this.updateToggleIcon(newTheme);
      });
    },

    loadTheme: function() {
      const savedTheme = localStorage.getItem('theme') || 'dark';
      document.documentElement.setAttribute('data-theme', savedTheme);
      this.updateToggleIcon(savedTheme);
    },

    updateToggleIcon: function(theme) {
      const icon = this.toggle.querySelector('i');
      if (icon) {
        icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
      }
    }
  };

  // Code highlighting
  const codeHighlight = {
    init: function() {
      this.setupCodeBlocks();
    },

    setupCodeBlocks: function() {
      const codeBlocks = document.querySelectorAll('pre code');
      
      codeBlocks.forEach(block => {
        // Add copy button
        const copyButton = document.createElement('button');
        copyButton.className = 'copy-button';
        copyButton.innerHTML = '复制';
        copyButton.setAttribute('aria-label', 'Copy code');
        
        const pre = block.parentNode;
        pre.style.position = 'relative';
        pre.appendChild(copyButton);

        copyButton.addEventListener('click', () => {
          navigator.clipboard.writeText(block.textContent).then(() => {
            copyButton.innerHTML = '已复制!';
            setTimeout(() => {
              copyButton.innerHTML = '复制';
            }, 2000);
          });
        });
      });
    }
  };

  // Table of Contents
  const toc = {
    init: function() {
      this.tocContainer = document.querySelector('.article-toc');
      if (!this.tocContainer) return;

      this.setupTOC();
    },

    setupTOC: function() {
      const headings = document.querySelectorAll('.article-content h1, .article-content h2, .article-content h3, .article-content h4, .article-content h5, .article-content h6');
      
      if (headings.length === 0) return;

      const tocList = this.tocContainer.querySelector('ul');
      
      headings.forEach((heading, index) => {
        const id = `heading-${index}`;
        heading.id = id;
        
        const li = document.createElement('li');
        li.className = `toc-${heading.tagName.toLowerCase()}`;
        li.innerHTML = `<a href="#${id}">${heading.textContent}</a>`;
        tocList.appendChild(li);
      });

      // Smooth scroll for TOC links
      tocList.addEventListener('click', (e) => {
        if (e.target.tagName === 'A') {
          e.preventDefault();
          const target = document.querySelector(e.target.getAttribute('href'));
          if (target) {
            target.scrollIntoView({
              behavior: 'smooth',
              block: 'start'
            });
          }
        }
      });
    }
  };

  // Initialize all components
  function init() {
    // Wait for DOM to be ready
    if (document.readyState === 'loading') {
      document.addEventListener('DOMContentLoaded', initComponents);
    } else {
      initComponents();
    }
  }

  function initComponents() {
    navbar.init();
    animations.init();
    lazyLoader.init();
    search.init();
    backToTop.init();
    themeToggle.init();
    codeHighlight.init();
    toc.init();
  }

  // Add CSS for dynamic elements
  const dynamicStyles = `
    .back-to-top {
      position: fixed;
      bottom: 2rem;
      right: 2rem;
      width: 50px;
      height: 50px;
      background: var(--color-primary);
      color: white;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      opacity: 0;
      visibility: hidden;
      transition: all 0.3s ease;
      z-index: 1000;
      font-size: 1.2rem;
      font-weight: bold;
    }

    .back-to-top.visible {
      opacity: 1;
      visibility: visible;
    }

    .back-to-top:hover {
      transform: translateY(-3px);
      box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
    }

    .copy-button {
      position: absolute;
      top: 0.5rem;
      right: 0.5rem;
      background: var(--bg-secondary);
      color: var(--text-secondary);
      border: 1px solid var(--border-primary);
      border-radius: 0.25rem;
      padding: 0.25rem 0.5rem;
      font-size: 0.75rem;
      cursor: pointer;
      transition: all 0.2s ease;
    }

    .copy-button:hover {
      background: var(--color-primary);
      color: white;
    }

    .navbar-hidden {
      transform: translateY(-100%);
    }

    .navbar.scrolled {
      background: rgba(15, 15, 35, 0.95);
      box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
    }

    .fade-in {
      opacity: 0;
      transform: translateY(30px);
      transition: all 0.6s ease;
    }

    .fade-in.animated {
      opacity: 1;
      transform: translateY(0);
    }

    .slide-up {
      opacity: 0;
      transform: translateY(50px);
      transition: all 0.8s ease;
    }

    .slide-up.animated {
      opacity: 1;
      transform: translateY(0);
    }

    .scale-in {
      opacity: 0;
      transform: scale(0.9);
      transition: all 0.5s ease;
    }

    .scale-in.animated {
      opacity: 1;
      transform: scale(1);
    }

    img.lazy {
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    img.loaded {
      opacity: 1;
    }

    mark {
      background: var(--color-warning);
      color: var(--text-primary);
      padding: 0.1em 0.2em;
      border-radius: 0.2em;
    }
  `;

  // Inject dynamic styles
  const styleSheet = document.createElement('style');
  styleSheet.textContent = dynamicStyles;
  document.head.appendChild(styleSheet);

  // Initialize the application
  init();

})();