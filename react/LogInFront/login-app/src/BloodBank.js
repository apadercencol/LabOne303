import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const BloodBank = () => {
    const [bloodBanks, setBloodBanks] = useState([]);
    const [error, setError] = useState("");
    const [newBloodBank, setNewBloodBank] = useState({
        bloodbankname: "",
        address: "",
        city: "",
        phone: "",
        website: "",
        email: "",
    });
    const navigate = useNavigate();

    useEffect(() => {
        fetchBloodBanks();
    }, []);

    const fetchBloodBanks = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/bloodbanks");
            setBloodBanks(response.data);
        } catch (error) {
            setError("Failed to load blood bank records.");
        }
    };

    const handleInputChange = (e, index, field) => {
        const updatedBloodBanks = [...bloodBanks];
        updatedBloodBanks[index][field] = e.target.value;
        setBloodBanks(updatedBloodBanks);
    };

    const handleUpdate = async (bloodBank) => {
        try {
            await axios.put("http://localhost:8080/api/bloodbanks/update", bloodBank);
            fetchBloodBanks(); // Refresh the data after update
        } catch (error) {
            setError("Failed to update blood bank record.");
        }
    };

    const handleNewInputChange = (e) => {
        const { name, value } = e.target;
        setNewBloodBank({ ...newBloodBank, [name]: value });
    };

    const handleAddNewBloodBank = async () => {
        try {
            await axios.post("http://localhost:8080/api/bloodbanks/add", newBloodBank);
            setNewBloodBank({
                bloodbankname: "",
                address: "",
                city: "",
                phone: "",
                website: "",
                email: "",
            });
            fetchBloodBanks(); // Refresh the data after adding
        } catch (error) {
            setError("Failed to add new blood bank.");
        }
    };

    return (
        <div style={{ textAlign: "center", marginTop: "20px" }}>
            <h1>Blood Bank Records</h1>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <table border="1" style={{ margin: "0 auto" }}>
                <thead>
                    <tr>
                        <th>Blood Bank Name</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>Phone</th>
                        <th>Website</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {bloodBanks.map((bloodBank, index) => (
                        <tr key={bloodBank.bloodbankname}>
                            <td>{bloodBank.bloodbankname}</td>
                            <td>
                                <input
                                    type="text"
                                    value={bloodBank.address}
                                    onChange={(e) => handleInputChange(e, index, "address")}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={bloodBank.city}
                                    onChange={(e) => handleInputChange(e, index, "city")}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={bloodBank.phone}
                                    onChange={(e) => handleInputChange(e, index, "phone")}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={bloodBank.website}
                                    onChange={(e) => handleInputChange(e, index, "website")}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={bloodBank.email}
                                    onChange={(e) => handleInputChange(e, index, "email")}
                                />
                            </td>
                            <td>
                                <button onClick={() => handleUpdate(bloodBank)}>Save</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <h2>Add New Blood Bank</h2>
            <div style={{ marginBottom: "20px" }}>
                <input
                    type="text"
                    name="bloodbankname"
                    placeholder="Blood Bank Name"
                    value={newBloodBank.bloodbankname}
                    onChange={handleNewInputChange}
                />
                <input
                    type="text"
                    name="address"
                    placeholder="Address"
                    value={newBloodBank.address}
                    onChange={handleNewInputChange}
                />
                <input
                    type="text"
                    name="city"
                    placeholder="City"
                    value={newBloodBank.city}
                    onChange={handleNewInputChange}
                />
                <input
                    type="text"
                    name="phone"
                    placeholder="Phone"
                    value={newBloodBank.phone}
                    onChange={handleNewInputChange}
                />
                <input
                    type="text"
                    name="website"
                    placeholder="Website"
                    value={newBloodBank.website}
                    onChange={handleNewInputChange}
                />
                <input
                    type="text"
                    name="email"
                    placeholder="Email"
                    value={newBloodBank.email}
                    onChange={handleNewInputChange}
                />
                <button onClick={handleAddNewBloodBank}>Add Blood Bank</button>
            </div>
            <button onClick={() => navigate("/home")}>Back to Home</button>
        </div>
    );
};

export default BloodBank;
