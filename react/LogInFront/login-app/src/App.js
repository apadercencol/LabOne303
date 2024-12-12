import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./Login";
import Home from "./Home";
import Profile from "./Profile";
import BloodBank from "./BloodBank";
import BloodStock from "./BloodStock"; // Import BloodStock component

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false); // Track login state

    const handleLogin = () => {
        setIsLoggedIn(true); // Set login state to true on successful login
    };

    const handleLogout = () => {
        setIsLoggedIn(false); // Logout and reset login state
        localStorage.removeItem("username"); // Clear username from localStorage
    };

    return (
        <Router>
            <Routes>
                {/* Public Route: Login */}
                <Route path="/" element={!isLoggedIn ? <Login onLogin={handleLogin} /> : <Navigate to="/home" />} />
                
                {/* Protected Route: Home */}
                <Route path="/home" element={isLoggedIn ? <Home onLogout={handleLogout} /> : <Navigate to="/" />} />
                
                {/* Protected Route: Profile */}
                <Route path="/profile" element={isLoggedIn ? <Profile /> : <Navigate to="/" />} />
                
                {/* Protected Route: BloodBank */}
                <Route path="/bloodbank" element={isLoggedIn ? <BloodBank /> : <Navigate to="/" />} />
                
                {/* Protected Route: BloodStock */}
                <Route path="/bloodstock" element={isLoggedIn ? <BloodStock /> : <Navigate to="/" />} />
            </Routes>
        </Router>
    );
};

export default App;
