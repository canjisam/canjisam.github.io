// Material Design 文章内容动画
document.addEventListener('DOMContentLoaded', function() {
  // 添加文章内容动画
  function addPostContentAnimations() {
    if (document.body.classList.contains('post')) {
      // 为标题添加动画
      const headings = document.querySelectorAll('.post-content h1, .post-content h2, .post-content h3, .post-content h4, .post-content h5, .post-content h6');
      
      headings.forEach((heading, index) => {
        heading.style.opacity = '0';
        heading.style.transform = 'translateX(-20px)';
        heading.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        
        // 使用Intersection Observer检测元素是否在视口中
        const observer = new IntersectionObserver((entries) => {
          entries.forEach(entry => {
            if (entry.isIntersecting) {
              setTimeout(() => {
                heading.style.opacity = '1';
                heading.style.transform = 'translateX(0)';
              }, index * 100);
              observer.unobserve(heading);
            }
          });
        }, { threshold: 0.1 });
        
        observer.observe(heading);
      });
      
      // 为图片添加动画
      const images = document.querySelectorAll('.post-content img');
      
      images.forEach((img) => {
        img.style.opacity = '0';
        img.style.transform = 'scale(0.9)';
        img.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        
        const observer = new IntersectionObserver((entries) => {
          entries.forEach(entry => {
            if (entry.isIntersecting) {
              img.style.opacity = '1';
              img.style.transform = 'scale(1)';
              observer.unobserve(img);
            }
          });
        }, { threshold: 0.1 });
        
        observer.observe(img);
      });
      
      // 为代码块添加动画
      const codeBlocks = document.querySelectorAll('.post-content pre');
      
      codeBlocks.forEach((block) => {
        block.style.opacity = '0';
        block.style.transform = 'translateY(20px)';
        block.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        
        const observer = new IntersectionObserver((entries) => {
          entries.forEach(entry => {
            if (entry.isIntersecting) {
              block.style.opacity = '1';
              block.style.transform = 'translateY(0)';
              observer.unobserve(block);
            }
          });
        }, { threshold: 0.1 });
        
        observer.observe(block);
      });
      
      // 为引用块添加动画
      const blockquotes = document.querySelectorAll('.post-content blockquote');
      
      blockquotes.forEach((quote) => {
        quote.style.opacity = '0';
        quote.style.transform = 'translateX(20px)';
        quote.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        
        const observer = new IntersectionObserver((entries) => {
          entries.forEach(entry => {
            if (entry.isIntersecting) {
              quote.style.opacity = '1';
              quote.style.transform = 'translateX(0)';
              observer.unobserve(quote);
            }
          });
        }, { threshold: 0.1 });
        
        observer.observe(quote);
      });
    }
  }
  
  // 添加Material Design风格的目录动画
  function addTocAnimations() {
    const tocItems = document.querySelectorAll('#card-toc .toc-item');
    
    tocItems.forEach((item, index) => {
      item.style.opacity = '0';
      item.style.transform = 'translateX(-10px)';
      item.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
      item.style.transitionDelay = `${index * 0.05}s`;
      
      setTimeout(() => {
        item.style.opacity = '1';
        item.style.transform = 'translateX(0)';
      }, 500 + index * 50);
    });
    
    // 添加目录项点击动画
    tocItems.forEach(item => {
      const link = item.querySelector('a');
      if (link) {
        link.addEventListener('click', function(e) {
          e.preventDefault();
          
          // 移除所有活动类
          tocItems.forEach(i => i.classList.remove('active'));
          
          // 添加活动类到当前项
          item.classList.add('active');
          
          // 获取目标ID
          const targetId = this.getAttribute('href').substring(1);
          const targetElement = document.getElementById(targetId);
          
          if (targetElement) {
            // 平滑滚动到目标
            window.scrollTo({
              top: targetElement.offsetTop - 80,
              behavior: 'smooth'
            });
          }
        });
      }
    });
  }
  
  // 执行动画函数
  setTimeout(() => {
    addPostContentAnimations();
    addTocAnimations();
  }, 500);
});