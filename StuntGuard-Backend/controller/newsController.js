const scraper = require('../modules/scraper');

// Controller to get the list of news articles
exports.getNews = async (req, res) => {
    try {
        const page = req.params.page ? parseInt(req.params.page) : 1;
        const result_type = req.params.result_type && req.params.result_type.trim() !== '' ? req.params.result_type : 'relevansi';
        if (result_type == 'relevansi' || result_type == 'latest') {
            const data = await scraper.getArticle(page, result_type);
            return res.status(200).json({
                status: true,
                message: "Data retrieved successfully",
                result: data,
                // hasNext: data.hasNext,
                // hasPrevious: data.hasPrevious,
                // currentPage: page
            });
        } else {
            return res.status(400).json({
                status: true,
                message: "Query is not valid",
            });
        }
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
        const result_type = req.params.result_type && req.params.result_type.trim() !== '' ? req.params.result_type : 'relevansi';
        if (result_type == 'relevansi' || result_type == 'latest') {
            if (!token) {
                throw new Error("Token parameter is required");
            }
            const data = await scraper.getDetail(token, page, result_type);
            return res.status(200).json({
                status: true,
                message: "Data retrieved successfully",
                result: data,
            });
        } else {
            return res.status(400).json({
                status: true,
                message: "Result type is not valid",
            });
        }
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
        currentPage = req.params.page;
        const result_type = req.params.result_type && req.params.result_type.trim() !== '' ? req.params.result_type : 'relevansi';
        currentPage++;
        res.redirect(`/news/${currentPage}/${result_type}`);
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
        currentPage = req.params.page;
        const result_type = req.params.result_type && req.params.result_type.trim() !== '' ? req.params.result_type : 'relevansi';
        if (currentPage > 1) {
            currentPage--;
        }
        res.redirect(`/news/${currentPage}/${result_type}`);
    } catch (err) {
        currentPage++;
        return res.status(500).json({
            status: false,
            message: "An error occurred",
            err: err.toString(),
        });
    }
};