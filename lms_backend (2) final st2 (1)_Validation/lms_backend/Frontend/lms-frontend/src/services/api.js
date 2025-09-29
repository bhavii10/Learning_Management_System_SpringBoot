
import axios from "axios";
import axiosRetry from "axios-retry";

// Create an Axios instance with base configuration
const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: { "Content-Type": "application/json" },
  timeout: 5000, // Prevent hanging requests
});

// Implement retry logic for transient errors
axiosRetry(api, {
  retries: 3,
  retryDelay: axiosRetry.exponentialDelay, // Exponential backoff
  retryCondition: (error) => {
    // Retry on network errors or 5xx server errors
    return (
      axiosRetry.isNetworkOrIdempotentRequestError(error) ||
      error.response?.status >= 500
    );
  },
});

// Interceptor to handle errors globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const errorMessage =
      error.response?.data?.message || error.message || "An unknown error occurred.";
    console.error("API Error:", errorMessage);
    return Promise.reject(new Error(errorMessage));
  }
);

//
// ================== AUTH ==================
export const registerUser = (payload) => api.post("/auth/register", payload);
export const loginUser = (payload) => api.post("/auth/login", payload);

//
// ================== COURSES ==================
export const getCourses = () => api.get("/courses");
export const getCourse = (id) => api.get(`/courses/${id}`);
export const createCourse = (payload) => api.post("/courses", payload);
export const updateCourse = (id, payload) => api.put(`/courses/${id}`, payload);
export const deleteCourse = (id) => api.delete(`/courses/${id}`);

//
// ================== LESSONS ==================
export const createLesson = (payload) => api.post("/lessons", payload);
export const updateLesson = (id, payload) => api.put(`/lessons/${id}`, payload);
export const deleteLesson = (id) => api.delete(`/lessons/${id}`);

// Toggle lesson completion (mark/unmark completed)
export const toggleLessonAPI = (lessonId, completed = false) =>
  api.put(`/lessons/${lessonId}/toggle`, { completed: !!completed });

//
// ================== ASSESSMENTS ==================
export const createAssessment = (payload) => api.post("/assessments", payload);
export const getAssessmentsByCourse = (courseId) => api.get(`/assessments/course/${courseId}`);
export const getAssessment = (id) => api.get(`/assessments/${id}`);
export const updateAssessment = (id, payload) => api.put(`/assessments/${id}`, payload);
export const deleteAssessment = (id) => api.delete(`/assessments/${id}`);

//
// ================== QUESTIONS ==================
export const createQuestion = (payload) => api.post("/questions", payload);
export const getQuestionsForAssessment = (assessmentId) => api.get(`/questions/assessment/${assessmentId}`);
export const getQuestionsForAssessmentAdmin = (assessmentId) => api.get(`/questions/admin/assessment/${assessmentId}`);





// ================== RESULTS ==================
export const submitResult = (payload) => api.post("/results/submit", payload);
export const getResult = (submissionId) => api.get(`/results/${submissionId}`);
export const getUserResults = (userId) => api.get(`/results/user/${userId}`);
export const getResultsByAssessment = (assessmentId) => api.get(`/results/assessment/${assessmentId}`);

// ================== CERTIFICATES ==================
// 📌 Generate and download course completion certificate
export const generateCertificateAPI = (payload) =>
  api.post("/certificates/generate", payload, { responseType: "blob" }); // ensure backend sends PDF as blob

export default api;


