import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = ({ onLogin }) => {
    const [isLogin, setIsLogin] = useState(true); // Toggle between Login and Register
    const [loginData, setLoginData] = useState({ userName: "", password: "" });
    const [registerData, setRegisterData] = useState({
        userName: "",
        password: "",
        firstname: "",
        lastname: "",
        phone: "",
        age_or_dob: "",
        gender: "",
        bloodgroup: "",
        city: "",
        last_donation: "",
        scheduled_donation: "",
        bloodbankname: "",
    });
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    // Handle input changes for login
    const handleLoginChange = (e) => {
        const { name, value } = e.target;
        setLoginData({ ...loginData, [name]: value });
    };

    // Handle input changes for registration
    const handleRegisterChange = (e) => {
        const { name, value } = e.target;
        setRegisterData({ ...registerData, [name]: value });
    };

    // Handle login submission
    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        try {
            const response = await axios.post("http://localhost:8080/api/donors/login", loginData);
            localStorage.setItem("username", loginData.userName); // Save username in localStorage
            onLogin(); // Trigger login callback
            navigate("/home"); // Redirect to Home
        } catch (error) {
            setError("Invalid username or password.");
        }
    };

    // Handle registration submission
    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        // Validate required fields
        if (!registerData.userName.trim() || !registerData.password.trim() || !registerData.firstname.trim() || !registerData.lastname.trim() || !registerData.phone.trim()) {
            setError("All required fields must be filled.");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/donors/register", registerData);
            setSuccess("Registration successful! You can now log in.");
            setRegisterData({
                userName: "",
                password: "",
                firstname: "",
                lastname: "",
                phone: "",
                age_or_dob: "",
                gender: "",
                bloodgroup: "",
                city: "",
                last_donation: "",
                scheduled_donation: "",
                bloodbankname: "",
            });
            setIsLogin(true); // Switch to login after successful registration
        } catch (error) {
            setError("Registration failed. Please try again.");
        }
    };

    // Toggle between Login and Register
    const toggleForm = () => {
        setIsLogin(!isLogin);
        setError("");
        setSuccess("");
    };

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>{isLogin ? "Login" : "Register"}</h1>
            {isLogin ? (
                <form onSubmit={handleLoginSubmit}>
                    <div>
                        <label>Username:</label>
                        <input type="text" name="userName" value={loginData.userName} onChange={handleLoginChange} />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input type="password" name="password" value={loginData.password} onChange={handleLoginChange} />
                    </div>
                    {error && <p style={{ color: "red" }}>{error}</p>}
                    <button type="submit">Login</button>
                    <p>
                        Don't have an account?{" "}
                        <span onClick={toggleForm} style={{ cursor: "pointer", color: "blue" }}>
                            Switch to Register
                        </span>
                    </p>
                </form>
            ) : (
                <form onSubmit={handleRegisterSubmit}>
                    <div>
                        <label>Username:</label>
                        <input type="text" name="userName" value={registerData.userName} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input type="password" name="password" value={registerData.password} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>First Name:</label>
                        <input type="text" name="firstname" value={registerData.firstname} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>Last Name:</label>
                        <input type="text" name="lastname" value={registerData.lastname} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>Phone:</label>
                        <input type="text" name="phone" value={registerData.phone} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>Age/DOB:</label>
                        <input type="text" name="age_or_dob" value={registerData.age_or_dob} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>Gender:</label>
                        <input type="text" name="gender" value={registerData.gender} onChange={handleRegisterChange} />
                    </div>
                    <div>
                        <label>City:</label>
                        <input type="text" name="city" value={registerData.city} onChange={handleRegisterChange} />
                    </div>
                    {error && <p style={{ color: "red" }}>{error}</p>}
                    {success && <p style={{ color: "green" }}>{success}</p>}
                    <button type="submit">Register</button>
                    <p>
                        Already have an account?{" "}
                        <span onClick={toggleForm} style={{ cursor: "pointer", color: "blue" }}>
                            Switch to Login
                        </span>
                    </p>
                </form>
            )}
        </div>
    );
};

export default Login;
