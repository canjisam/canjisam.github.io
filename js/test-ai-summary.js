// 测试讯飞星火API调用
async function testSparkAPI() {
  const url = 'https://spark-api-open.xf-yun.com/v1/chat/completions';
  
  // 请求配置
  const config = {
    API_KEY: 'uctVoWDLOekIRLAvQTSb:uxdGkqjnOQetITBGrhIt',
    MODEL: '4.0Ultra'
  };

  try {
    // 构建请求头
    const headers = {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${config.API_KEY}`
    };

    // 构建请求体
    const payload = {
      max_tokens: 4096,
      top_k: 4,
      temperature: 0.5,
      messages: [
        {
          role: 'system',
          content: ''
        },
        {
          role: 'user',
          content: '请在此处输入你的问题!!!'
        }
      ],
      model: config.MODEL,
      stream: true
    };

    console.log('发送请求...');
    console.log('请求头:', headers);
    console.log('请求体:', payload);

    // 发送请求
    const response = await fetch(url, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      throw new Error(`API请求失败: ${response.status}`);
    }

    // 处理流式响应
    const reader = response.body.getReader();
    const decoder = new TextDecoder('utf-8');

    while (true) {
      const { done, value } = await reader.read();
      if (done) break;
      
      // 解码并输出响应数据
      const text = decoder.decode(value);
      console.log(text);
    }

  } catch (error) {
    console.error('测试过程中发生错误:', error);
    throw error;
  }
}

// 运行测试
console.log('开始测试讯飞星火API...');
testSparkAPI()
  .then(() => {
    console.log('测试成功完成！');
  })
  .catch(error => {
    console.error('测试失败:', error);
  });