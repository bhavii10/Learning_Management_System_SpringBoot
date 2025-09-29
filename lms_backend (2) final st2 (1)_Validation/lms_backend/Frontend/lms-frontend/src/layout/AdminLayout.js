// src/layouts/AdminLayout.js
import React from 'react';
import Navbar from '../components/Navbar';

export default function AdminLayout({ children }) {
  return (
    <div>
      <Navbar />
      <div>{children}</div>
    </div>
  );
}
