const prisma = require('../prisma/prisma');
const predictClassification = require('../services/inference'); // Sesuaikan dengan path
const { storePredict } = require('../services/storeData'); // Sesuaikan dengan path

exports.createPrediction = async (req, res) => {
  try {
    const { body_weight, body_length, breastfeeding: breastfeedingInput } = req.body;
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

    const breastfeeding = breastfeedingInput !== undefined ? breastfeedingInput : booleanToBreastfeeding(child.breastfeeding);
    // Prepare data for prediction
    const predictionData = {
      Gender: booleanToGender(child.child_gender),
      Age: child.child_age,
      'Birth Weight': child.birth_weight,
      'Birth Length': child.birth_height,
      'Body Weight': body_weight,
      'Body Length': body_length,
      Breastfeeding: breastfeeding
    };

    // Run the prediction
    const prediction = await predictClassification(predictionData);

    // Save prediction to the database
    let updatedChild;
    const breastfeedingBoolean = breastfeeding === "Yes";
    if (breastfeedingInput !== undefined && breastfeedingBoolean !== child.breastfeeding) {
      updatedChild = await prisma.child.update({
        where: { child_id: child_id },
        data: {
          breastfeeding: breastfeedingBoolean(breastfeeding),
        }
      });
    }

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

exports.getAllPredicts = async (req, res) => {
  try {
    const predictions = await prisma.prediction.findMany();
    res.status(200).json({
      status: true,
      message: 'All predictions retrieved successfully.',
      data: predictions,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal server error.' });
  }
}

// Get a specific prediction by ID
exports.getPredictbyId = async (req, res) => {
  const { predict_id } = req.params;

  try {
    const prediction = await prisma.prediction.findUnique({
      where: { predict_id: predict_id },
    });

    if (!prediction) {
      return res.status(404).json({
        status: false,
        message: 'Prediction not found.',
      });
    }

    res.status(200).json({
      status: true,
      message: 'Prediction retrieved successfully.',
      data: prediction,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal server error.' });
  }
}

// Get all predictions for a specific child
exports.getAllPredictbyChild = async (req, res) => {
  const { child_id } = req.params;

  try {
    const predictions = await prisma.prediction.findMany({
      where: { child_id: child_id },
    });

    res.status(200).json({
      status: true,
      message: 'All predictions for the child retrieved successfully.',
      data: predictions,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal server error.' });
  }
}
