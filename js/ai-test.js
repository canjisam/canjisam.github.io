const url = "https://spark-api-open.xf-yun.com/v1/chat/completions";
const data = {
    max_tokens: 4096,
    top_k: 4,
    temperature: 0.5,
    messages: [
        {
            role: "system",
            content: "你是一位具有高度凝练能力的作者,目标任务,请根据我给出的文本，归纳核心要点并提供一段文本摘要,需求说明,要求重点突出，中心思想清晰,风格设定,简洁明了"
        },
        {
            role: "user",
            content: "---title: 计算机专业竞赛推荐date: 2025-03-10 12:14:00tags: [计算机专业, 竞赛推荐]categories:- 计算机专业- 竞赛推荐---"
        }
    ],
    model: "4.0Ultra"
};
data.stream = true;
const header = {
    "Authorization": "Bearer uctVoWDLOekIRLAvQTSb:uxdGkqjnOQetITBGrhIt",
    "Content-Type": "application/json"
};

async function fetchData() {
    const response = await fetch(url, {
        method: 'POST',
        headers: header,
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder('utf-8');

    while (true) {
        const { done, value } = await reader.read();
        if (done) break;
        console.log(decoder.decode(value));
    }
}

fetchData().catch(error => console.error('There has been a problem with your fetch operation:', error));



