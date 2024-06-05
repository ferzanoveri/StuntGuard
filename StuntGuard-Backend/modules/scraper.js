const axios = require('axios');
const cheerio = require('cheerio');
const crypto = require('crypto');

const base = "https://www.detik.com/search/searchall?query=stunting+pada+anak";

// Function to get the list of news articles
exports.getIndex = async (page = 1) => {
    try {
        const url = `${base}&page=${page}`;
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

            // Generate a token for the link
            const token = crypto.createHash('sha256').update(link).digest('hex');
            result.push({ token, link, image, title, date, publisher });
        });

        const hasNext = $("a.pagination__item.pagination__item--next").length > 0;
        const hasPrevious = $("a.pagination__item.pagination__item--previous").length > 0;

        return { result, hasNext, hasPrevious };
    } catch (error) {
        throw error; // Tangkap dan lemparkan error untuk menangani di controller
    }
};

// Function to get detailed information of a news article
exports.getDetail = async (token) => {
    try {
        const articles = await exports.getIndex();
        const article = articles.result.find(article => article.token === token);

        if (!article) {
            throw new Error('Article not found');
        }

        const response = await axios.get(article.link);
        const html_data = response.data;
        const $ = cheerio.load(html_data);

        const title = $('h1').text().trim();
        const date = $('div.detail__date').text().trim();
        const image = $(".media__image img").attr('src');

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
