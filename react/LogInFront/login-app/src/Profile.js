import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Profile = () => {
    const [profile, setProfile] = useState({
        firstname: "",
        lastname: "",
        phone: "",
        age_or_dob: "",
        gender: "",
        city: "",
        last_donation: "",
        scheduled_donation: "",
        bloodgroup: "",
        bloodbankname: "",
    });
    const [newDonationDate, setNewDonationDate] = useState(""); // State for the new scheduled donation date
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            const username = localStorage.getItem("username");
            if (!username) {
                setError("No logged-in user found.");
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/api/donors/profile/${username}`);
                setProfile(response.data);
            } catch (error) {
                setError("Failed to load profile information.");
            }
        };
        fetchProfile();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfile({ ...profile, [name]: value });
    };

    const handleSave = async () => {
        const username = localStorage.getItem("username");
        if (!username) {
            setError("No logged-in user found.");
            return;
        }

        try {
            const response = await axios.put("http://localhost:8080/api/donors/update", {
                ...profile,
                userName: username,
            });
            setSuccess(response.data);
        } catch (error) {
            setError("Failed to save profile updates.");
        }
    };

    const handleScheduleDonation = async () => {
        const username = localStorage.getItem("username");
        if (!username) {
            setError("No logged-in user found.");
            return;
        }

        if (!newDonationDate) {
            setError("Please select a date for the new donation.");
            return;
        }

        try {
            const response = await axios.put("http://localhost:8080/api/donors/update", {
                ...profile,
                scheduled_donation: newDonationDate,
                userName: username,
            });
            setSuccess("Donation date scheduled successfully!");
            setProfile({ ...profile, scheduled_donation: newDonationDate });
            setNewDonationDate(""); // Clear the date input
        } catch (error) {
            setError("Failed to schedule the donation.");
        }
    };

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Profile</h1>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}
            <div style={{ textAlign: "left", display: "inline-block" }}>
                <div>
                    <label>First Name:</label>
                    <input
                        type="text"
                        name="firstname"
                        value={profile.firstname}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Last Name:</label>
                    <input
                        type="text"
                        name="lastname"
                        value={profile.lastname}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Phone:</label>
                    <input
                        type="text"
                        name="phone"
                        value={profile.phone}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Age/DOB:</label>
                    <input
                        type="text"
                        name="age_or_dob"
                        value={profile.age_or_dob}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Gender:</label>
                    <input
                        type="text"
                        name="gender"
                        value={profile.gender}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>City:</label>
                    <input
                        type="text"
                        name="city"
                        value={profile.city}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Blood Group:</label>
                    <input
                        type="text"
                        name="bloodgroup"
                        value={profile.bloodgroup}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Blood Bank Name:</label>
                    <input
                        type="text"
                        name="bloodbankname"
                        value={profile.bloodbankname}
                        onChange={handleChange}
                    />
                </div>
                <button onClick={handleSave}>Save</button>
            </div>

            {/* Blood Donation History */}
            <div style={{ marginTop: "20px", textAlign: "center" }}>
                <h3>Blood Donation History</h3>
                <p>
                    <strong>Last Donation:</strong> {profile.last_donation || "N/A"}
                </p>
                <p>
                    <strong>Scheduled Donation:</strong> {profile.scheduled_donation || "N/A"}
                </p>
            </div>

            {/* Schedule New Donation Section */}
            <div style={{ marginTop: "20px", textAlign: "center" }}>
                <h3>Schedule New Donation?</h3>
                <input
                    type="date"
                    value={newDonationDate}
                    onChange={(e) => setNewDonationDate(e.target.value)}
                />
                <button onClick={handleScheduleDonation} style={{ marginLeft: "10px" }}>
                    Schedule
                </button>
            </div>

            {/* Back Button */}
            <div style={{ marginTop: "20px" }}>
                <button onClick={() => navigate("/home")}>Back</button>
            </div>
        </div>
    );
};

export default Profile;
