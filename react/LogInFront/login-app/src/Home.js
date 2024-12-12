import React from "react";
import { useNavigate } from "react-router-dom";

const Home = ({ onLogout }) => {
    const navigate = useNavigate();

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Welcome to the Home Page!</h1>
            <button onClick={() => navigate("/profile")}>View Profile</button>
            <button onClick={() => navigate("/bloodbank")}>Blood Bank</button>
            <button onClick={() => navigate("/bloodstock")}>Blood Stock</button> {/* New Button */}
            <button onClick={onLogout}>Logout</button>
        </div>
    );
};

export default Home;

