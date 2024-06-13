const axios = require('axios');
const dotenv = require('dotenv');

dotenv.config();

const BASE_URL = process.env.ML_URL;

exports.postPredict = async (req, res) => {
    const { child_id } = req.params;
    try {
        const response = await axios.post(`${BASE_URL}/predict/${child_id}`, req.body);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getAllPredictions = async (req, res) => {
    try {
        const response = await axios.get(`${BASE_URL}/predict/all`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getPredictionById = async (req, res) => {
    const { predict_id } = req.params;
    try {
        const response = await axios.get(`${BASE_URL}/predict/id/${predict_id}`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getPredictionsByChild = async (req, res) => {
    const { child_id } = req.params;
    try {
        const response = await axios.get(`${BASE_URL}/predict/child/${child_id}`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.postNotes = async (req, res) => {
    const { predict_id } = req.params;
    try {
        const response = await axios.post(`${BASE_URL}/notes/${predict_id}`, req.body);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

exports.getNotes = async (req, res) => {
    const { predict_id } = req.params;
    try {
        const response = await axios.get(`${BASE_URL}/get_notes/${predict_id}`);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
