//import React from 'react';
//import { BrowserRouter, Routes, Route } from 'react-router-dom';
//import Home from './pages/Home';
//import Login from './pages/Login';
//import Register from './pages/Register';
//import CoursesList from './pages/courses/CoursesList';
//import CourseDetails from './pages/courses/CourseDetails';
//import AdminCourses from './pages/courses/AdminCourses';
//import AdminCourseForm from './pages/courses/AdminCourseForm';
//import Navbar from './components/Navbar';
//import ProtectedRoute from './components/ProtectedRoute';
//
//// Assessments
//import AdminAssessments from './pages/admin/AdminAssessments';
//import ManageQuestions from './pages/admin/ManageQuestions';
//import MyAssessments from './pages/user/MyAssessments';
//import TakeAssessment from './pages/user/TakeAssessment';
//
//function UserDashboard() {
//  return (
//    <div style={{ padding: 24 }}>
//      <h2>User Dashboard</h2>
//      <p>Welcome to your personalized dashboard!</p>
//    </div>
//  );
//}
//
//function AdminDashboard() {
//  return (
//    <div style={{ padding: 24 }}>
//      <h2>Admin Dashboard</h2>
//      <p>Manage courses and assessments here.</p>
//    </div>
//  );
//}
//
//export default function App() {
//  return (
//    <BrowserRouter>
//      <Navbar />
//      <Routes>
//        {/* Public Pages */}
//        <Route path="/" element={<Home />} />
//        <Route path="/login" element={<Login />} />
//        <Route path="/register" element={<Register />} />
//        <Route path="/courses" element={<CoursesList />} />
//        <Route path="/courses/:id" element={<CourseDetails />} />
//
//        {/* Admin Routes */}
//        <Route
//          path="/admin"
//          element={
//            <ProtectedRoute allowedRoles={['ADMIN']}>
//              <AdminDashboard />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/admin/courses"
//          element={
//            <ProtectedRoute allowedRoles={['ADMIN']}>
//              <AdminCourses />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/admin/new"
//          element={
//            <ProtectedRoute allowedRoles={['ADMIN']}>
//              <AdminCourseForm />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/admin/assessments"
//          element={
//            <ProtectedRoute allowedRoles={['ADMIN']}>
//              <AdminAssessments />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/admin/assessments/:assessmentId/questions"
//          element={
//            <ProtectedRoute allowedRoles={['ADMIN']}>
//              <ManageQuestions />
//            </ProtectedRoute>
//          }
//        />
//
//        {/* User Routes */}
//        <Route
//          path="/user"
//          element={
//            <ProtectedRoute allowedRoles={['USER']}>
//              <UserDashboard />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/user/assessments"
//          element={
//            <ProtectedRoute allowedRoles={['USER']}>
//              <MyAssessments />
//            </ProtectedRoute>
//          }
//        />
//        <Route
//          path="/assessments/:assessmentId/take"
//          element={
//            <ProtectedRoute allowedRoles={['USER']}>
//              <TakeAssessment />
//            </ProtectedRoute>
//          }
//        />
//      </Routes>
//    </BrowserRouter>
//  );
//}



import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import CoursesList from './pages/courses/CoursesList';
import CourseDetails from './pages/courses/CourseDetails';
import AdminCourses from './pages/courses/AdminCourses';
import AdminCourseForm from './pages/courses/AdminCourseForm';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';

// Assessments
import AdminAssessments from './pages/admin/AdminAssessments';
import ManageQuestions from './pages/admin/ManageQuestions';
import MyAssessments from './pages/user/MyAssessments';
import TakeAssessment from './pages/user/TakeAssessment';

// ✅ Dashboards
import AdminDashboard from './pages/admin/AdminDashboard';
import UserDashboard from './pages/user/UserDashboard';

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Public Pages */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/courses" element={<CoursesList />} />
        <Route path="/courses/:id" element={<CourseDetails />} />

        {/* Admin Routes */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/courses"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminCourses />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/new"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminCourseForm />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/assessments"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminAssessments />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/assessments/:assessmentId/questions"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <ManageQuestions />
            </ProtectedRoute>
          }
        />

        {/* User Routes */}
        <Route
          path="/user"
          element={
            <ProtectedRoute allowedRoles={['USER']}>
              <UserDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/user/assessments"
          element={
            <ProtectedRoute allowedRoles={['USER']}>
              <MyAssessments />
            </ProtectedRoute>
          }
        />
        <Route
          path="/assessments/:assessmentId/take"
          element={
            <ProtectedRoute allowedRoles={['USER']}>
              <TakeAssessment />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}