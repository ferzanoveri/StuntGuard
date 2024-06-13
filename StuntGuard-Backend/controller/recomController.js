const axios = require('axios');
const dotenv = require('dotenv');

dotenv.config();

const BASE_URL = process.env.ML_URL;

exports.postRecom = async (req, res) => {
    const { predict_id } = req.params;
    try {
        const response = await axios.post(`${BASE_URL}/recom/${predict_id}`, req.body);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getAllRecommendations = async (req, res) => {
    try {
        const response = await axios.get(`${BASE_URL}/recom/all`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getRecommendationsByChild = async (req, res) => {
    const { child_id } = req.params;
    try {
        const response = await axios.get(`${BASE_URL}/recom/child/${child_id}`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getFoodDetailsByRecommendation = async (req, res) => {
    const { recommendation_id } = req.params;
    try {
        const response = await axios.get(`${BASE_URL}/recom/id/${recommendation_id}/foods`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
