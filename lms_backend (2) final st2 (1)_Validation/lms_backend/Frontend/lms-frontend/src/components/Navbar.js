import React from 'react';
import './Navbar.css';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

export default function Navbar() {
  const location = useLocation();
  const user = JSON.parse(localStorage.getItem('user') || 'null');
  const navigate = useNavigate();
  // Hide navbar on login and register pages
  if (location.pathname === '/login' || location.pathname === '/register') {
    return null;
  }

  return (
    <nav>
      <Link to="/">Home</Link> |{' '}
      <Link to="/courses">Courses</Link> |{' '}

      {/* Admin Navbar */}
      {user?.role === 'ADMIN' && (
        <>
          <Link to="/admin">Admin Dashboard</Link> |{' '}
          <Link to="/admin/courses">Manage Courses</Link> |{' '}
          <Link to="/admin/new">Add Course</Link> |{' '}
          <Link to="/admin/assessments">Manage Assessments</Link> |{' '}
        </>
      )}

      {/* User Navbar */}
      {user?.role === 'USER' && (
        <>
          <Link to="/user">My Dashboard</Link> |{' '}
          <Link to="/user/assessments">My Assessments</Link> |{' '}
        </>
      )}

      {/* Auth */}
      {user ? (
        <button
          onClick={() => {
            localStorage.removeItem('user');
            navigate('/login');
          }}
        >
          Logout
        </button>

      ) : (
        <Link to="/login">Login</Link>
      )}
    </nav>
  );
}