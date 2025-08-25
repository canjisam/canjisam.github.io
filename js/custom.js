// 页面加载动画
document.addEventListener('DOMContentLoaded', function() {
  // 创建加载动画元素
  const loadingBox = document.createElement('div');
  loadingBox.id = 'loading-box';
  
  const spinner = document.createElement('div');
  spinner.className = 'loading-spinner';
  
  loadingBox.appendChild(spinner);
  document.body.appendChild(loadingBox);
  
  // 页面加载完成后移除加载动画
  window.addEventListener('load', function() {
    loadingBox.classList.add('loaded');
    setTimeout(() => {
      loadingBox.remove();
    }, 300);
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