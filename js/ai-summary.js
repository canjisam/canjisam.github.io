document.addEventListener('DOMContentLoaded', function() {
  const aiSummaryContent = document.querySelector('.ai-summary-content');
  if (!aiSummaryContent) return;

  // 添加加载状态
  const loadingHtml = `
    <div class="ai-summary-loading">
      <i class="fas fa-spinner fa-spin"></i>
      <span>正在生成AI摘要...</span>
    </div>
  `;
  aiSummaryContent.innerHTML = loadingHtml;

  // 获取文章内容
  const articleContent = document.querySelector('.post-content').innerText;
  const articleTitle = document.querySelector('.post-title').innerText;

  // 调用AI摘要生成
  generateSummary(articleTitle, articleContent)
    .then(summary => {
      updateSummaryContent(summary);
    })
    .catch(error => {
      aiSummaryContent.innerHTML = `
        <div class="ai-summary-error">
          <i class="fas fa-exclamation-circle"></i>
          <span>生成摘要时发生错误: ${error.message}</span>
        </div>
      `;
    });
});

async function generateSummary(title, content) {
  // 讯飞开放平台配置
  const API_KEY = process.env.XF_API_KEY;
  const API_SECRET = process.env.XF_API_SECRET;
  const API_URL = 'https://spark-api.xf-yun.com/v2.1/chat';

  // 生成请求参数
  const date = new Date().toUTCString();
  const requestId = crypto.randomUUID();

  // 生成签名
  const signature = await generateSignature(API_KEY, API_SECRET, date);

  // 构建请求头
  const headers = {
    'Content-Type': 'application/json',
    'Authorization': `api_key="${API_KEY}", algorithm="hmac-sha256", headers="host date request-line", signature="${signature}"`,
    'Date': date,
    'Host': 'spark-api.xf-yun.com'
  };

  // 构建请求体
  const payload = {
    header: {
      app_id: API_KEY,
      uid: requestId
    },
    parameter: {
      chat: {
        domain: 'general',
        temperature: 0.5,
        max_tokens: 1024
      }
    },
    payload: {
      message: {
        text: [
          {
            role: 'user',
            content: `请为这篇文章生成一个简短的摘要，标题是：${title}\n内容是：${content}`
          }
        ]
      }
    }
  };

  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      throw new Error(`API请求失败: ${response.status}`);
    }

    const result = await response.json();
    
    // 解析API返回的摘要内容
    const summary = {
      mainPoints: parseAISummary(result.payload.choices.text[0].content)
    };

    return summary;
  } catch (error) {
    console.error('生成摘要时发生错误:', error);
    throw error;
  }
}

// 生成API签名
async function generateSignature(apiKey, apiSecret, date) {
  const signatureOrigin = `host: spark-api.xf-yun.com\ndate: ${date}\nPOST /v2.1/chat HTTP/1.1`;
  
  const encoder = new TextEncoder();
  const data = encoder.encode(signatureOrigin);
  const key = encoder.encode(apiSecret);
  
  const cryptoKey = await crypto.subtle.importKey(
    'raw',
    key,
    { name: 'HMAC', hash: 'SHA-256' },
    false,
    ['sign']
  );
  
  const signature = await crypto.subtle.sign(
    'HMAC',
    cryptoKey,
    data
  );
  
  return btoa(String.fromCharCode(...new Uint8Array(signature)));
}

// 解析AI返回的摘要内容
function parseAISummary(content) {
  // 这里可以根据实际API返回的格式进行解析
  // 示例中假设返回的是用换行符分隔的多个要点
  return content.split('\n').filter(point => point.trim());
}

function updateSummaryContent(summary) {
  const aiSummaryContent = document.querySelector('.ai-summary-content');
  const summaryHtml = `
    <p class="ai-summary-text">这是一篇关于本主题的文章，主要内容包括：</p>
    <div class="ai-summary-points">
      <ul>
        ${summary.mainPoints.map(point => `<li>${point}</li>`).join('')}
      </ul>
    </div>
    <div class="ai-summary-footer">
      <span class="ai-summary-note">注：此总结由AI自动生成，仅供参考</span>
    </div>
  `;
  aiSummaryContent.innerHTML = summaryHtml;
}