import axios from "axios";
import { useState } from "react";

const LoginPage = () => {

    const [formData, setFormData] = useState({
        username:"",
        password:""
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        setError("");

        try {

            setLoading(true);
            const response = await axios.post('http://localhost:9090/api/auth/signin', formData)
            setMessage(response.data);

        } catch (error) {

            if (err.response && err.response.data) {
                setError(err.response.data.message || "Signup failed")
            } else {
                setError("Server Not Responding");
            }

        } finally {

            setLoading(false);

        }


    };

    return (
        <>
            <div className="bg-white w-full rounded-lg p-8 shadow-md">
                <h2 className="text-2xl font-semibold text-center mb-6 text-gray-800">Login</h2>

                {message && <div className="mb-4 text-green-600 font-medium">{message}</div>}
                {error && <div className="mb-4 text-red-600 font-medium">{error}</div>}

                <form onSubmit={handleSubmit} className="space-y-4">

                    <input 
                        type="text"
                        name="username"
                        placeholder="Username"
                        value={formData.username}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded"
                        required 
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded"
                        required
                    />

                    <button type="submit"
                            disabled= {loading}
                            className={`w-full py-2 rounded-lg text-white ${loading 
                                ? 'bg-gray-400 cursor-not-allowed'
                                : 'bg-gradient-to-br from-blue-500 to-violet-600 hover:from-blue-700 hover:to-violet-800'}`}>
                                    Login
                    </button>

                </form>

            </div>
        </>
    )

}

export default LoginPage;