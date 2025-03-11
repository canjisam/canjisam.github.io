document.addEventListener('DOMContentLoaded', function() {
  const aiSummaryContent = document.querySelector('.ai-summary-content');
  if (!aiSummaryContent) return;

  // 添加生成按钮
  const generateButtonHtml = `
    <div class="ai-summary-button">
      <button id="generateAiSummary" class="btn-ai-summary">
        <i class="fas fa-magic"></i>
        <span>生成AI摘要</span>
      </button>
    </div>
  `;
  aiSummaryContent.innerHTML = generateButtonHtml;

  // 添加点击事件监听器（优化防重复点击）
  const generateBtn = document.getElementById('generateAiSummary');
  let isProcessing = false;

  generateBtn.addEventListener('click', function() {
    if (isProcessing) return;
    isProcessing = true;
    // 添加加载状态
    const loadingHtml = `
      <div class="ai-summary-loading">
        <i class="fas fa-spinner fa-spin"></i>
        <span>正在生成AI摘要...</span>
        <div class="loading-progress"></div>
      </div>
    `;
    aiSummaryContent.innerHTML = loadingHtml;

    // 获取文章内容
    const articleContent = document.querySelector('.post-content').innerText;
    const articleTitle = document.querySelector('.post-title').innerText;

    // 调用AI摘要生成
    generateStreamSummary(articleTitle, articleContent)
      .catch(error => {
        console.error('生成摘要时发生错误:', error);
        // 添加网络状态检测
        if (!navigator.onLine) {
          error.message = '网络连接不可用，请检查网络设置';
        }
        
        const errorHtml = `
          <div class="ai-summary-error">
            <i class="fas fa-exclamation-circle"></i>
            <div class="error-content">
              <p>生成摘要时发生错误：${error.message}</p>
              <button class="btn-retry" id="retryButton">
                <i class="fas fa-redo"></i>
                点击重试
              </button>
            </div>
          </div>
        `;
        aiSummaryContent.innerHTML = errorHtml;
        
        // 使用事件委托绑定重试按钮
        aiSummaryContent.addEventListener('click', function(event) {
          if (event.target.closest('#retryButton')) {
            isProcessing = false;
            event.target.closest('.ai-summary-error').remove();
            generateBtn.click();
          }
        });
        
        isProcessing = false;
      });
  });}
);

async function generateStreamSummary(title, content) {
  const aiSummaryContent = document.querySelector('.ai-summary-content');
  if (!aiSummaryContent) return;

  try {
    // 从服务器获取API配置
    const response = await fetch('/api/config');
    if (!response.ok) {
      throw new Error('无法获取API配置');
    }
    const config = await response.json();
    const { API_KEY, API_SECRET } = config;

    // 检查API密钥是否存在
    if (!API_KEY || !API_SECRET) {
      throw new Error('API配置无效');
    }

    // 准备显示流式响应的容器
    aiSummaryContent.innerHTML = `
      <p class="ai-summary-text">这是一篇关于本主题的文章，主要内容包括：</p>
      <div class="ai-summary-stream"></div>
      <div class="ai-summary-footer">
        <span class="ai-summary-note">注：此总结由AI自动生成，仅供参考</span>
      </div>
    `;
    
    const streamContainer = aiSummaryContent.querySelector('.ai-summary-stream');
    streamContainer.innerHTML = '<p>正在生成摘要...</p>';

    // WebSocket连接重试逻辑
    let retryCount = 0;
    const maxRetries = 3;
    const retryDelay = 1000;

    const connectWebSocket = async () => {
      try {
        const date = new Date().toUTCString();
        const API_HOST = 'spark-api.xf-yun.com';
        const API_PATH = '/v3.1/chat';
        const tmp = `host: ${API_HOST}\ndate: ${date}\nGET ${API_PATH} HTTP/1.1`;
        const signature = CryptoJS.HmacSHA256(tmp, API_SECRET).toString(CryptoJS.enc.Base64);
        const authorization_origin = `api_key="${API_KEY}", algorithm="hmac-sha256", headers="host date request-line", signature="${signature}"`;
        const authorization = encodeURIComponent(authorization_origin);
        const API_URL = `wss://${API_HOST}${API_PATH}?authorization=${authorization}&date=${encodeURIComponent(date)}&host=${API_HOST}`;

        const ws = new WebSocket(API_URL);
        let isConnected = false;

        return new Promise((resolve, reject) => {
          const timeout = setTimeout(() => {
            if (!isConnected) {
              ws.close();
              reject(new Error('WebSocket连接超时'));
            }
          }, 10000);

          ws.onopen = () => {
            isConnected = true;
            clearTimeout(timeout);
            resolve(ws);
          };

          ws.onerror = (error) => {
            clearTimeout(timeout);
            reject(error);
          };
        });
      } catch (error) {
        throw new Error(`WebSocket连接失败: ${error.message}`);
      }
    };

    const connectWithRetry = async () => {
      while (retryCount < maxRetries) {
        try {
          return await connectWebSocket();
        } catch (error) {
          retryCount++;
          if (retryCount === maxRetries) {
            throw error;
          }
          await new Promise(resolve => setTimeout(resolve, retryDelay * retryCount));
        }
      }
    };

    const ws = await connectWithRetry();

    // 构建请求数据
    const data = {
      max_tokens: 4096,
      top_k: 4,
      temperature: 0.5,
      stream: true,
      messages: [
        {
          role: "system",
          content: "你是一位具有高度凝练能力的作者,目标任务,请根据我给出的文本，归纳核心要点并提供一段文本摘要,需求说明,要求重点突出，中心思想清晰,风格设定,简洁明了"
        },
        {
          role: "user",
          content: `标题：${title}\n内容：${content.substring(0, 2000)}` // 限制内容长度，避免请求过大
        }
      ],
      model: "4.0Ultra"
    };

    // 发送数据
    ws.send(JSON.stringify(data));

    let summaryText = '';
    
    // 处理WebSocket消息
    ws.onmessage = (event) => {
      try {
        const response = JSON.parse(event.data);
        if (response.choices && response.choices[0] && response.choices[0].delta && response.choices[0].delta.content) {
          const content = response.choices[0].delta.content;
          summaryText += content;
          streamContainer.innerHTML = formatSummaryContent(summaryText);
        }
      } catch (error) {
        console.error('解析响应数据时出错:', error);
      }
    };

    // 处理WebSocket关闭
    ws.onclose = () => {
      if (summaryText) {
        streamContainer.innerHTML = formatSummaryContent(summaryText);
      } else {
        throw new Error('WebSocket连接已关闭');
      }
    };

  } catch (error) {
    console.error('生成摘要时发生错误:', error);
    throw error;
  }
}

// 格式化摘要内容
function formatSummaryContent(text) {
  if (!text || text.trim() === '') {
    return '<p>正在生成摘要...</p>';
  }
  
  // 简单格式化：将文本按段落分割并添加HTML标记
  const paragraphs = text.split('\n\n').filter(p => p.trim());
  
  if (paragraphs.length > 1) {
    // 多段落格式
    return paragraphs.map(p => `<p>${p.replace(/\n/g, '<br>')}</p>`).join('');
  } else {
    // 单段落，尝试提取要点
    const points = text.split(/\d+[.、]\s*/).filter(p => p.trim());
    
    if (points.length > 1) {
      return `<ul>${points.map(point => `<li>${point.trim()}</li>`).join('')}</ul>`;
    } else {
      // 没有明确的要点格式，直接显示文本
      return `<p>${text.replace(/\n/g, '<br>')}</p>`;
    }
  }
}