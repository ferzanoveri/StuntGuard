const axios = require('axios');
const cheerio = require('cheerio');
const crypto = require('crypto');

const base = "https://www.detik.com/search/searchall?query=stunting%20pada%20anak";

// Function to get the list of news articles
exports.getIndex = async (page = 1, type) => {
    try {
        const url = `${base}&page=${page}&result_type=${type}`;
        const response = await axios.get(url);
        const html_data = response.data;
        const $ = cheerio.load(html_data);
        const result = [];

        const selectedElement = $("article.list-content__item");
        selectedElement.each(function () {
            const link = $(this).find('a.media__link').attr('href');
            const image = $(this).find(".media__image img").attr('src');
            const title = $(this).find('a.media__link').attr('dtr-ttl');
            const date = $(this).find('div.media__date').text().trim();
            const publisher = $(this).find('h2.media__subtitle').text().trim();

            const excludedPublishers = ["20Detik"]
            if (link.includes('stunting') &&  !link.includes('video') && !excludedPublishers.includes(publisher)) {
                // Generate a token for the link
                const token = crypto.createHash('sha256').update(link).digest('hex').slice(0,8);
                result.push({ token, link, image, title, date, publisher });
            }
        });
        
        return result;
    } catch (error) {
        throw error; 
    }
};

// Function to get all article
exports.getArticle = async (page, type) => {
    try {
        const promises = [];

        for (let i = page; i < 50; i++) {
            promises.push(this.getIndex(i, type));
        }
        const hasil = await Promise.all(promises);
        const mergedResults = [].concat(...hasil); 
        return mergedResults;
} catch (error) {
    throw error; 
}
}

// Function to get detailed information of a news article
exports.getDetail = async (token, page, type) => {
    try {
        const articles = await exports.getArticle(page, type);
        const article = articles.find(article => article.token === token);

        if (!article) {
            throw new Error('Article not found');
        }

        const response = await axios.get(article.link);
        const html_data = response.data;
        const $ = cheerio.load(html_data);

        const title = $('h1').text().trim();
        const date = $('div.detail__date').text().trim();
        const image = $(".p_img_zoomin.img-zoomin").attr('src');

        // Extract the author's label and content
        const authorLabel = $('div.detail__author').text().trim();
        const label = $('div.detail__author span.detail__label').text().trim();
        const author = $('div.detail__author').children().remove('span.detail__label').end().text().trim();

        // Extract content from <p> elements within 'div.detail__body-text' that do not have a class attribute
        const content = $('div.detail__body-text p').map((i, el) => {
            if (!$(el).attr('class')) {
                return $(el).text().trim();
            }
            return '';
        }).get().filter(text => text !== '').join(''); 

        return { title, date, authorLabel, label, author, image, content };
    } catch (error) {
        throw error; // Tangkap dan lemparkan error untuk menangani di controller
    }
};
