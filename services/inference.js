const tf = require('@tensorflow/tfjs-node');
const loadModel = require('./loadModel');

async function predictClassification(data) {
  // Load your model
  const model = await loadModel();

  // Preprocess data according to your ML model
  const tensor = tf.tensor([[
    data.Gender,
    data.Age,
    data['Birth Weight'],
    data['Birth Length'],
    data['Body Weight'],
    data['Body Length'],
    data.Breastfeeding
  ]]);

  // Predict classification
  const prediction = model.predict(tensor);
  const score = await prediction.data();
  const confidenceScore = Math.max(...score) * 100;

  const classes = ['Yes','No']; // Replace with actual class labels from your model

  const classResult = tf.argMax(prediction, 1).dataSync()[0];
  const label = classes[classResult];

  return { confidenceScore, label };
}

module.exports = predictClassification;
