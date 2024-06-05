const scraper = require('../modules/scraper');

// Controller to get the list of news articles
exports.getNews = async (req, res) => {
    try {
        const page = req.params.page ? parseInt(req.params.page) : 1;
        const data = await scraper.getIndex(page);
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
        if (!token) {
            throw new Error("Token parameter is required");
        }
        const data = await scraper.getDetail(token);
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
