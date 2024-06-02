const prisma = require('../prisma/prisma');
const predictClassification = require('../services/inference'); // Sesuaikan dengan path
const { storePredict } = require('../services/storeData'); // Sesuaikan dengan path

async function createPrediction(req, res) {
  try {
    const { body_weight, body_length } = req.body;
    const { child_id } = req.params;

    // Fetch child data from the database
    const child = await prisma.child.findUnique({
      where: { child_id: child_id }
    });

    if (!child) {
      return res.status(404).json({ error: 'Child not found' });
    }

    const booleanToGender = (genderBoolean) => {
      return genderBoolean ? "Male" : "Female";
    };

    const booleanToBreastfeeding = (breastfeedingBoolean) => {
      return breastfeedingBoolean ? "Yes" : "No";
    };

    // Prepare data for prediction
    const predictionData = {
      Gender: booleanToGender(child.child_gender),
      Age: child.child_age,
      'Birth Weight': child.born_weight,
      'Birth Length': child.born_height,
      'Body Weight': body_weight,
      'Body Length': body_length,
      Breastfeeding: booleanToBreastfeeding(child.breastfeeding)
    };

    // Run the prediction
    const prediction = await predictClassification(predictionData);

    // Save prediction to the database
    const savedPrediction = await storePredict({
      child_weight: body_weight,
      child_height: body_length,
      predict_result: prediction.label,
      child_id: child_id
    });

    res.status(201).json({
      message: 'Prediction saved successfully.',
      prediction: savedPrediction
    });
  } catch (error) {
    console.error('Error creating prediction:', error);
    res.status(500).json({ error: 'Internal server error.' });
  }
}

module.exports = { createPrediction };
