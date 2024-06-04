// const axios = require('axios');
// const cheerio = require('cheerio');
// // const { response } = require('express');
// // const url = "https://www.cnnindonesia.com/tag/kesehatan-anak"

// exports.getNews = async (query) =>{
//   const base = "https://www.cnnindonesia.com/tag/kesehatan-anak"
//   try {
//     const response = await axios.get(base+query);
//     const html_data = response.data;
//     const $ = cheerio.load(html_data);
//     const result = [];

//     const selectedElement = $("article[flex-grow]");
//     selectedElement.each(function(){
//       link = $(this).find('article > a').attr('href')
//       image = $(this).find(".w-full").attr('src')
//       title = $(this).find('h1[mb-2 text-[28px] leading-9 text-cnn_black]').text()
//       publisher = $(this).find('span[text-xs text-cnn_black_light3]').text()
//       date = publisher = $(this).find('span[text-cnn_red]').text()
//       price = $(this).find('.price-container.price-final_price.tax.weee').first().text()
//       result.push({link, image, title, price})
//     })
//     return result.slice(0, 5);
// } catch (error) {
//     throw error; 
// }
    
    
// }
