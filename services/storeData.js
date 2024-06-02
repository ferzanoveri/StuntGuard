const prisma = require('../prisma/prisma');
const { nanoid } = require('nanoid');

async function storePredict(data) {
  try {
    const predictData = {
      predict_id: nanoid(8),
      child_weight: data.child_weight,
      child_height: data.child_height,
      predict_result: data.predict_result,
      child_id: data.child_id,
    };

    const result = await prisma.stuntPredict.create({
      data: predictData,
    });

    return result;
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
