# Backend API Documentation 🧑‍💻

## How to run this API on your local machine 💻

If you want to run this API Server on your local machine, you need to do this steps:

Clone this repository. `git clone -b backend https://github.com/ferzanoveri/StuntGuard.git`

### Backend Service
1. Navigate to the directory using `cd StuntGuard-Backend`.
2. Install all required dependencies by typing `npm ci` or `npm i` in the terminal.
3. Remember to create your `.env` file according to your settings.
4. Ensure your XAMPP is running and create a database with the same name as specified in your `.env` file.
5. If needed, run `npx prisma generate` before migrating your databases with `npx prisma migrate dev`.
6. Start your application by running `npm run start-dev` in the terminal.

### Machine Learning
1. Navigate to the directory using `cd StuntGuard-ML`.
2. Install all necessary requirements by typing `pip install -r requirements.txt` in the terminal.
3. Make sure XAMPP is running and that you have a database set up as before.
4. Create your `.env` file with the required configurations.
5. Run your application using `python app.py` in the terminal.

## StuntGuard API Endpoints

### Auth
| Endpoint             | Method | Input                                  | Description           | Status       |
|----------------------|--------|----------------------------------------|-----------------------|--------------|
| /register            | POST   | parent_name, email, phone, password, confirmPassword | Register account | ✅ Completed |
| /login               | POST   | email, password                        | Login to application  | ✅ Completed |

### Users
| Endpoint                 | Method | Input                                  | Description           | Status       |
|--------------------------|--------|----------------------------------------|-----------------------|--------------|
| /user/all                | GET    | -                                      | Get all parents data  | ✅ Completed |
| /user/id/:parent_id      | GET    | parent_id                              | Get parent data by ID | ✅ Completed |
| /user/update/:parent_id  | PUT   | parent_name, email, phone (optional)   | Update parent data    | ✅ Completed |
| /user/updatePassword/:parent_id | PUT   | oldPassword, newPassword, confirmPassword | Update parent password | ✅ Completed |
| /user/remove/:parent_id  | DELETE | -                                      | Remove parent account | ✅ Completed |

### Children
| Endpoint                        | Method | Input                                           | Description                   | Status       |
|---------------------------------|--------|-------------------------------------------------|-------------------------------|--------------|
| /child/:parent_id               | POST   | child_name, child_gender, birth_date, birth_weight, birth_height, breastfeeding | Add child data to family | ✅ Completed |
| /child/update/:child_id          | PUT   | child_name, child_gender, birth_date, birth_weight, birth_height, breastfeeding | Update child data          | ✅ Completed |
| /parent/childs/:parent_id       | GET    | need parent_id params                                       | Get children data by parent ID | ✅ Completed |
| /child/all                      | GET    | -                                               | Get all children data         | ✅ Completed |
| /child/id/:child_id             | GET    | need child_id params                                        | Get child data by ID          | ✅ Completed |
| /child/remove/:child_id         | DELETE | -                                               | Remove child data              | ✅ Completed |

### News
| Endpoint                          | Method | Input                       | Description                   | Status       |
|-----------------------------------|--------|-----------------------------|-------------------------------|--------------|
| /news           | GET   | -  | Get all news and redirect automatically to result type relevance page 1.              | ✅ Completed   |
| /news/:result_type?   | GET   | -  | Get news category between 'latest' or 'relevansi'       | ✅ Completed   |
| /news/:result_type?/:token                 | GET    | token                           | Get all news details           | ✅ Completed   |

### Predictions
| Endpoint                          | Method | Input                       | Description                   | Status       |
|-----------------------------------|--------|-----------------------------|-------------------------------|--------------|
| /predict/:child_id             | POST   | child_weight, child_height, breastfeeding(optional)  | Post a prediction             | 🟠 Model on development   |
| /predict/all                  | GET    | -                           | Get all predictions           | ✅ Completed   |
| /predict/id/:predict_id       | GET    | need predict_id params                 | Get predictions by predict_id | ✅ Completed   |
| /predict/child/:child_id   | GET    | need child_id params                   | Get predictions by child_id   | ✅ Completed   |

### Notes
| Endpoint                            | Method | Input           | Description                                 | Status       |
|-------------------------------------|--------|-----------------|---------------------------------------------|--------------|
| /notes/:predict_id                  | POST   | need predict_id params       | Add a note to a prediction                  | ✅ Completed |
| /get_notes/:predict_id              | GET    | need predict_id params       | Get notes for a prediction by prediction ID | ✅ Completed |

### Recommendations
| Endpoint                            | Method | Input           | Description                                 | Status       |
|-------------------------------------|--------|-----------------|---------------------------------------------|--------------|
| /recom/:predict_id                  | POST   | need predict_id params       | Add a recommendation to a prediction         | ✅ Completed  |
| /recom/all                          | GET    | -               | Get all recommendations                      | ✅ Completed      |
| /recom/child/:child_id              | GET    | need child_id params        | Get recommendations by child ID              | ✅ Completed      |
| /recom/id/:recommendation_id/foods  | GET    | need recommendation_id params | Get food details for a recommendation by recommendation ID | ✅ Completed |

# Cloud Services

## ![Cloud Architecture](images/CloudArchitecture.png)

## Deskripsi Layanan Cloud

### Cloud Run
Cloud Run is used as a backend service and to deploy machine learning models and API endpoints using containerized services.

### Cloud Storage
Cloud Storage is used to store assets and machine learning models.

### Cloud SQL
Cloud SQL is used to store relational data such as user data, child data, and prediction results from machine learning models.

### Postman
Postman is used to test the backend API. Provides tools for developing, testing, and documenting APIs.

## ![Database Diagram](images/DatabaseDiagram.png)
