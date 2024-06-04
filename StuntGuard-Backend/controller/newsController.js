const axios = require('axios');
const cheerio = require('cheerio');

exports.getIndex = async () =>{
    const base = "https://www.detik.com/search/searchall?query=kesehatan+anak"
    try {
      const response = await axios.get(base);
      const html_data = response.data;
      const $ = cheerio.load(html_data);
      const result = [];
  
      const selectedElement = $("article.list-content__item");
      selectedElement.each(function(){
        link = $(this).find(' a.media__link').attr('href')
        image = $(this).find(".media__image img").attr('src')
        title = $(this).find('a.media__link').attr('dtr-ttl')
        // idk apakah ini akan realtime ganti
        date = $(this).find('div.media__date').text()
        publisher = $(this).find('h2.media__subtitle').text()
        // detail = $(this).find('div.detail__body-text ito-bodycontent').text()
        result.push({link, image, title, publisher})
      })
      return result.slice(0, 5);
  } catch (error) {
      throw error; // Tangkap dan lemparkan error untuk menangani di controller
  }
      
      
  }

exports.getNews = async (req, res) => {
    try {
        // var query= req.params.query
        
        // if (!query || typeof query !== "string") {
        //     throw new Error("Invalid query parameter");
        // }

//   query = query.replace(/ /g, "+");
      const data = await exports.getIndex();
      return res.status(200).json({
        status: true,
        message: "Data retrieved sucessfully",
        result: data,
      });
    } catch (err) {
      return res.status(500).json({
        status: false,
        message: "An error occured",
        err: err.toString(),
      });
    }
}
