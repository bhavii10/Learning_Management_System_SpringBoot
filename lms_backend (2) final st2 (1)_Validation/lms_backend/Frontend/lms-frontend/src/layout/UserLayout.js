// src/layouts/UserLayout.js
import React from 'react';
import Navbar from '../components/Navbar';

export default function UserLayout({ children }) {
  return (
    <div>
      <Navbar />
      <div>{children}</div>
    </div>
  );
}
