// Material Design 页面加载动画
document.addEventListener('DOMContentLoaded', function() {
  // 创建加载动画元素
  const loadingBox = document.createElement('div');
  loadingBox.id = 'loading-box';
  
  // Material Design 风格的加载动画
  const spinner = document.createElement('div');
  spinner.className = 'material-spinner';
  
  // 添加波普艺术风格的颜色
  const colors = [
    'var(--primary-color)',
    'var(--secondary-color)',
    'var(--accent-color)',
    'var(--pop-purple)',
    'var(--pop-orange)'
  ];
  
  // 创建Material Design环形加载动画
  for (let i = 0; i < 4; i++) {
    const circle = document.createElement('div');
    circle.className = 'spinner-circle';
    circle.style.borderTopColor = colors[i % colors.length];
    circle.style.animationDelay = `${i * 0.15}s`;
    spinner.appendChild(circle);
  }
  
  loadingBox.appendChild(spinner);
  document.body.appendChild(loadingBox);
  
  // 添加波普艺术风格的加载文字
  const loadingText = document.createElement('div');
  loadingText.className = 'loading-text';
  loadingText.innerHTML = 'LOADING';
  loadingBox.appendChild(loadingText);
  
  // 页面加载完成后移除加载动画
  window.addEventListener('load', function() {
    loadingBox.classList.add('loaded');
    setTimeout(() => {
      loadingBox.remove();
    }, 600);
  });
  
  // 标签云和分类列表动画效果
  const tagLinks = document.querySelectorAll('.tag-cloud-list a');
  if (tagLinks.length > 0) {
    tagLinks.forEach((link, index) => {
      link.style.animationDelay = `${index * 0.05}s`;
      link.classList.add('tag-appear');
    });
  }
  
  const categoryItems = document.querySelectorAll('.category-list-item');
  if (categoryItems.length > 0) {
    categoryItems.forEach((item, index) => {
      item.style.animationDelay = `${index * 0.05}s`;
      item.classList.add('category-appear');
    });
  }
  
  // 文章列表动画效果
  const articleItems = document.querySelectorAll('.recent-post-item');
  if (articleItems.length > 0) {
    articleItems.forEach((item, index) => {
      item.style.animationDelay = `${index * 0.1}s`;
      item.classList.add('article-appear');
    });
  }
  
  // 添加阅读进度条
  if (document.body.classList.contains('post')) {
    const progressBar = document.createElement('div');
    progressBar.className = 'reading-progress-bar';
    document.body.appendChild(progressBar);
    
    window.addEventListener('scroll', function() {
      const docHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      const scrollPercent = (scrollTop / docHeight) * 100;
      progressBar.style.width = scrollPercent + '%';
    });
  }
});

// 添加标签云和分类列表的动画样式
const style = document.createElement('style');
style.textContent = `
  @keyframes tagAppear {
    from {
      opacity: 0;
      transform: translateY(20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
  
  .tag-appear {
    animation: tagAppear 0.5s ease forwards;
    opacity: 0;
  }
  
  @keyframes categoryAppear {
    from {
      opacity: 0;
      transform: translateX(-20px);
    }
    to {
      opacity: 1;
      transform: translateX(0);
    }
  }
  
  .category-appear {
    animation: categoryAppear 0.5s ease forwards;
    opacity: 0;
  }
  
  /* Material Design 加载动画样式 */
  #loading-box {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: var(--bg-color, #FAFAFA);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    z-index: 9999;
    transition: opacity 0.6s ease, visibility 0.6s ease;
  }
  
  #loading-box.loaded {
    opacity: 0;
    visibility: hidden;
  }
  
  .material-spinner {
    position: relative;
    width: 60px;
    height: 60px;
  }
  
  .spinner-circle {
    position: absolute;
    width: 100%;
    height: 100%;
    border: 4px solid transparent;
    border-radius: 50%;
    animation: spin 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  }
  
  .spinner-circle:nth-child(1) {
    width: 100%;
    height: 100%;
    animation-delay: -0.45s;
  }
  
  .spinner-circle:nth-child(2) {
    width: 80%;
    height: 80%;
    top: 10%;
    left: 10%;
    animation-delay: -0.3s;
  }
  
  .spinner-circle:nth-child(3) {
    width: 60%;
    height: 60%;
    top: 20%;
    left: 20%;
    animation-delay: -0.15s;
  }
  
  .spinner-circle:nth-child(4) {
    width: 40%;
    height: 40%;
    top: 30%;
    left: 30%;
    animation-delay: 0s;
  }
  
  @keyframes spin {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
  
  .loading-text {
    margin-top: 20px;
    font-family: var(--font-family, 'Roboto');
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 4px;
    background: linear-gradient(90deg, 
      var(--primary-color, #FF4081), 
      var(--secondary-color, #00E5FF), 
      var(--accent-color, #FFEA00), 
      var(--pop-purple, #AA00FF), 
      var(--pop-orange, #FF6D00));
    background-size: 200% auto;
    color: transparent;
    -webkit-background-clip: text;
    background-clip: text;
    animation: gradient 2s linear infinite;
  }
  
  @keyframes gradient {
    0% {
      background-position: 0% center;
    }
    100% {
      background-position: 200% center;
    }
  }
  
  /* 波纹效果样式 */
  .ripple {
    position: relative;
    overflow: hidden;
  }
  
  .ripple-effect {
    position: absolute;
    border-radius: 50%;
    background-color: rgba(255, 255, 255, 0.4);
    width: 100px;
    height: 100px;
    margin-top: -50px;
    margin-left: -50px;
    animation: ripple 0.6s linear;
    transform: scale(0);
    opacity: 1;
    pointer-events: none;
  }
  
  @keyframes ripple {
    to {
      transform: scale(4);
      opacity: 0;
    }
  }
  
  @keyframes articleAppear {
    from {
      opacity: 0;
      transform: translateY(30px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
  
  .article-appear {
    animation: articleAppear 0.6s ease forwards;
    opacity: 0;
  }
`;
document.head.appendChild(style);

// Material Design 波纹效果
document.addEventListener('DOMContentLoaded', function() {
  // 添加波纹效果到按钮和可点击元素
  function addRippleEffect() {
    const buttons = document.querySelectorAll('button, .button, .post-reward-button, .error-button, .search-button, .page-number, .menu-item, .tag-cloud-tags a, .social-icon, .flink-list-item');
    
    buttons.forEach(button => {
      if (!button.classList.contains('ripple')) {
        button.classList.add('ripple');
        
        button.addEventListener('click', function(e) {
          const rect = button.getBoundingClientRect();
          const x = e.clientX - rect.left;
          const y = e.clientY - rect.top;
          
          const ripple = document.createElement('span');
          ripple.className = 'ripple-effect';
          ripple.style.left = x + 'px';
          ripple.style.top = y + 'px';
          
          button.appendChild(ripple);
          
          setTimeout(() => {
            ripple.remove();
          }, 600);
        });
      }
    });
  }
  
  // 添加Material Design风格的站点名称动画
  function addSiteNameAnimation() {
    const siteName = document.getElementById('site-name');
    if (siteName) {
      const text = siteName.textContent;
      siteName.textContent = '';
      
      // 为每个字符创建单独的span元素
      for (let i = 0; i < text.length; i++) {
        const span = document.createElement('span');
        span.className = 'site-name-animation';
        span.style.animationDelay = `${i * 0.05}s`;
        span.textContent = text[i];
        siteName.appendChild(span);
      }
    }
  }
  
  // 初始添加波纹效果和站点名称动画
  addRippleEffect();
  addSiteNameAnimation();
  
  // 当DOM变化时重新添加波纹效果
  const observer = new MutationObserver(function(mutations) {
    addRippleEffect();
  });
  
  observer.observe(document.body, { childList: true, subtree: true });
  
  // Material Design 风格的滚动动画
  function smoothScroll(target, duration) {
    const targetElement = document.querySelector(target);
    if (!targetElement) return;
    
    const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset;
    const startPosition = window.pageYOffset;
    const distance = targetPosition - startPosition;
    let startTime = null;
    
    function animation(currentTime) {
      if (startTime === null) startTime = currentTime;
      const timeElapsed = currentTime - startTime;
      const run = ease(timeElapsed, startPosition, distance, duration);
      window.scrollTo(0, run);
      if (timeElapsed < duration) requestAnimationFrame(animation);
    }
    
    // Material Design 缓动函数
    function ease(t, b, c, d) {
      t /= d / 2;
      if (t < 1) return c / 2 * t * t + b;
      t--;
      return -c / 2 * (t * (t - 2) - 1) + b;
    }
    
    requestAnimationFrame(animation);
  }
  
  // 为锚点链接添加平滑滚动
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();
      const target = this.getAttribute('href');
      smoothScroll(target, 600);
    });
  });
});

// 代码块复制功能
document.addEventListener('DOMContentLoaded', function() {
  // 为所有代码块添加复制按钮
  const codeBlocks = document.querySelectorAll('.highlight');
  if (codeBlocks.length > 0) {
    codeBlocks.forEach((block, index) => {
      // 创建工具栏
      if (!block.querySelector('.highlight-tools')) {
        const tools = document.createElement('div');
        tools.className = 'highlight-tools';
        
        // 添加语言标签
        const language = block.className.split(' ').find(cls => cls !== 'highlight');
        const langLabel = document.createElement('span');
        langLabel.className = 'code-lang';
        langLabel.textContent = language ? language.replace('language-', '') : 'code';
        tools.appendChild(langLabel);
        
        // 添加复制按钮
        const copyBtn = document.createElement('span');
        copyBtn.className = 'copy-button';
        copyBtn.innerHTML = '<i class="fas fa-copy"></i>';
        copyBtn.title = '复制代码';
        copyBtn.onclick = function() {
          const code = block.querySelector('code').textContent;
          navigator.clipboard.writeText(code).then(() => {
            copyBtn.innerHTML = '<i class="fas fa-check"></i>';
            setTimeout(() => {
              copyBtn.innerHTML = '<i class="fas fa-copy"></i>';
            }, 2000);
          }).catch(err => {
            console.error('复制失败:', err);
            copyBtn.innerHTML = '<i class="fas fa-times"></i>';
            setTimeout(() => {
              copyBtn.innerHTML = '<i class="fas fa-copy"></i>';
            }, 2000);
          });
        };
        tools.appendChild(copyBtn);
        
        // 插入工具栏到代码块前
        block.insertBefore(tools, block.firstChild);
      }
    });
  }
  
  // 图片点击放大效果
  const postImages = document.querySelectorAll('.post-content img');
  if (postImages.length > 0) {
    postImages.forEach(img => {
      img.onclick = function() {
        if (!document.querySelector('.img-lightbox')) {
          const lightbox = document.createElement('div');
          lightbox.className = 'img-lightbox';
          lightbox.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.8);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
            cursor: zoom-out;
          `;
          
          const imgClone = document.createElement('img');
          imgClone.src = this.src;
          imgClone.style.cssText = `
            max-width: 90%;
            max-height: 90%;
            object-fit: contain;
            border-radius: 5px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.3);
            transition: all 0.3s ease;
          `;
          
          lightbox.appendChild(imgClone);
          document.body.appendChild(lightbox);
          
          lightbox.onclick = function() {
            this.remove();
          };
        }
      };
    });
  }
});