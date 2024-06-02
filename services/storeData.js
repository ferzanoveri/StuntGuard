const prisma = require('../prisma/prisma');
const moment = require('moment');

async function storePredict(data) {
  try {
    const dateId = moment().format('YYYYMMDD'); // Generate date-based ID

    const predictData = {
      predict_id: dateId,
      child_weight: data.child_weight,
      child_height: data.child_height,
      predict_result: data.predict_result,
      child_id: data.child_id,
    };

    // Check if a prediction already exists for the current date
    const existingPrediction = await prisma.stuntPredict.findUnique({
      where: { predict_id: dateId },
    });

    if (existingPrediction) {
      // Update the existing prediction
      const result = await prisma.stuntPredict.update({
        where: { predict_id: dateId },
        data: predictData,
      });
      return result;
    } else {
      // Create a new prediction
      const result = await prisma.stuntPredict.create({
        data: predictData,
      });
      return result;
    }
  } catch (error) {
    throw new Error(`Error storing predict data: ${error.message}`);
  }
}

async function storeRecom(data) {
  try {
    const recomData = {
      recommendation_id: nanoid(8),
      child_id: data.child_id,
      food_details: {
        create: data.food_details.map((detail) => ({
          food_id: nanoid(16),
          ingredients: detail.ingredients,
          nutritions: detail.nutritions,
          measure: detail.measure,
        })),
      },
    };

    const result = await prisma.recommendation.create({
      data: recomData,
    });

    return result;
  } catch (error) {
    throw new Error(`Error storing recommendation data: ${error.message}`);
  }
}

module.exports = { storePredict, storeRecom };
