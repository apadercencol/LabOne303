import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const BloodStock = () => {
    const [bloodStock, setBloodStock] = useState([]);
    const [error, setError] = useState("");
    const [newStock, setNewStock] = useState({
        bloodGroup: "",
        quantity: "",
        bestBefore: "",
        status: "",
    });
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBloodStock = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/bloodstock");
                setBloodStock(response.data);
            } catch (err) {
                setError("Failed to fetch blood stock records.");
            }
        };
        fetchBloodStock();
    }, []);

    const handleInputChange = (index, field, value) => {
        const updatedBloodStock = [...bloodStock];
        updatedBloodStock[index][field] = value;
        setBloodStock(updatedBloodStock);
    };

    const handleNewStockChange = (field, value) => {
        setNewStock({ ...newStock, [field]: value });
    };

    const handleSave = async (index) => {
        const updatedRecord = bloodStock[index];
        try {
            const response = await axios.put("http://localhost:8080/api/bloodstock/update", updatedRecord);
            alert(response.data); // Display success message
        } catch (err) {
            alert("Failed to update blood stock record.");
        }
    };

    const handleAddNewStock = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/bloodstock/add", newStock);
            alert(response.data); // Display success message
            setBloodStock([...bloodStock, newStock]); // Add new stock to the table
            setNewStock({ bloodGroup: "", quantity: "", bestBefore: "", status: "" }); // Reset form fields
        } catch (err) {
            alert("Failed to add new blood stock record. Ensure the blood group is unique.");
        }
    };

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Blood Stock Records</h1>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <table border="1" style={{ margin: "0 auto", width: "80%", borderCollapse: "collapse" }}>
                <thead>
                    <tr>
                        <th>Blood Group</th>
                        <th>Quantity</th>
                        <th>Best Before</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {bloodStock.map((stock, index) => (
                        <tr key={stock.bloodGroup}>
                            <td>{stock.bloodGroup}</td>
                            <td>
                                <input
                                    type="number"
                                    value={stock.quantity}
                                    onChange={(e) => handleInputChange(index, "quantity", e.target.value)}
                                />
                            </td>
                            <td>
                                <input
                                    type="date"
                                    value={stock.bestBefore.split("T")[0]} // Format date for input
                                    onChange={(e) => handleInputChange(index, "bestBefore", e.target.value)}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={stock.status}
                                    onChange={(e) => handleInputChange(index, "status", e.target.value)}
                                />
                            </td>
                            <td>
                                <button onClick={() => handleSave(index)}>Save</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Add New Blood Stock Section */}
            <div style={{ marginTop: "30px" }}>
                <h3>Add New Blood Stock</h3>
                <div>
                    <label>Blood Group: </label>
                    <input
                        type="text"
                        value={newStock.bloodGroup}
                        onChange={(e) => handleNewStockChange("bloodGroup", e.target.value)}
                    />
                </div>
                <div>
                    <label>Quantity: </label>
                    <input
                        type="number"
                        value={newStock.quantity}
                        onChange={(e) => handleNewStockChange("quantity", e.target.value)}
                    />
                </div>
                <div>
                    <label>Best Before: </label>
                    <input
                        type="date"
                        value={newStock.bestBefore}
                        onChange={(e) => handleNewStockChange("bestBefore", e.target.value)}
                    />
                </div>
                <div>
                    <label>Status: </label>
                    <input
                        type="text"
                        value={newStock.status}
                        onChange={(e) => handleNewStockChange("status", e.target.value)}
                    />
                </div>
                <button onClick={handleAddNewStock} style={{ marginTop: "10px" }}>
                    Add Blood Stock
                </button>
            </div>

            <button onClick={() => navigate("/home")} style={{ marginTop: "20px" }}>
                Back to Home
            </button>
        </div>
    );
};

export default BloodStock;
