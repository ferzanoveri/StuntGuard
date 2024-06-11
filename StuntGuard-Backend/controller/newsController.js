const scraper = require('../modules/scraper');

// Controller to get the list of news articles
exports.getNews = async (req, res) => {
    try {
        const page = req.params.page ? parseInt(req.params.page) : 1;
        const result_type = req.params.result_type && req.params.result_type.trim() !== '' ? req.params.result_type : 'relevansi';
        const data = await scraper.getIndex(page, result_type);
        
        return res.status(200).json({
            status: true,
            message: "Data retrieved successfully",
            result: data.result,
            hasNext: data.hasNext,
            hasPrevious: data.hasPrevious,
            currentPage: page
        });
    } catch (err) {
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};

// Controller to get detailed information for a specific news article by token
exports.getNewsDetails = async (req, res) => {
    try {
        const { token } = req.params;
        const page = req.params.page ? parseInt(req.params.page) : 1;
        if (!token) {
            throw new Error("Token parameter is required");
        }
        const data = await scraper.getDetail(token, page);
        return res.status(200).json({
            status: true,
            message: "Data retrieved successfully",
            result: data,
        });
    } catch (err) {
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};

let currentPage = 1;

// Controller to get the next page of news articles
exports.getNextPage = async (req, res) => {
    try {
        currentPage++;
        res.redirect(`/news/${currentPage}`);
    } catch (err) {
        currentPage--;
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};

// Controller to get the previous page of news articles
exports.getPreviousPage = async (req, res) => {
    try {
        if (currentPage > 1) {
            currentPage--;
        }
        res.redirect(`/news/${currentPage}`);
    } catch (err) {
        currentPage++;
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};

exports.getRelevansi = async (req, res) => {
    try{
        if (type = 'relevansi');
        res.redirect(`/news/${currentPage}`/relevansi);
    } catch (err) {
        type = 'terbaru';
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};

exports.getTerbaru = async (req, res) => {
    try{
        if (type = 'terbaru');
        res.redirect(`/news/${currentPage}`/terbaru);
    } catch (err) {
        type = 'relevansi';
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};