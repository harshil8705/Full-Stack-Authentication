import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const HomePage = () => {

    const [message, setMessage] = useState("");
    const [greetMessage, setGreetMessage] = useState("");
    const [error, setError] = useState("");
    const location = useLocation();
    const navigate = useNavigate();
    const responseMessage = location.state?.message || "";

    useEffect(() => {
        const fetchUsername = async () => {
            try {
                const userResponse = await axios.get('http://localhost:9090/api/auth/username', {
                    withCredentials: true,
                });
                setGreetMessage(userResponse.data);
            } catch (err) {
                setError("Failed to fetch username");
            }
        };
        fetchUsername();
    }, [])

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await axios.post('http://localhost:9090/api/auth/sign-out');
            setMessage(response.data);

            const username = await axios.get('http://localhost:9090/api/auth/username');
            setGreetMessage(username.data);

            setTimeout(() => {
                navigate("/", {state: {message: "Signed Out Successfully"}})
            }, 1000)

        } catch (err) {

            if (err.response && err.response.data) {
                setError(err.response.data.message || "Sign-out failed")
            } else {
                setError("Server Not Responding");
            }

        }

    };

    return (
        <>
            <div className="h-screen w-full bg-gradient-to-br from-cyan-300 to-fuchsia-600 via-blue-500">
                <div className="w-full backdrop-blur-2xl bg-white/30 p-1 flex justify-end">
                    <button 
                        type="button"
                        className="bg-gradient-to-br from-blue-500 to-violet-600 px-3 py-1 rounded-lg hover:from-blue-700 hover:to-violet-800 text-white text-xl font-sans"
                        onClick={handleSubmit}>
                        Sign Out
                    </button>
                </div>
                {responseMessage && (
                    <div className="flex justify-center items-center bg-white mt-8 text-green-600 text-lg font-medium">{responseMessage}</div>
                )}
                {message && (
                    <div className="flex justify-center items-center bg-white mt-8 text-green-600 text-lg font-medium">{message}</div>
                )}
                {error && (
                    <div className="flex justify-center items-center bg-white mt-8 text-red-600 text-lg font-medium">{error}</div>
                )}

                <div className="text-6xl font-black text-white flex justify-center items-center mt-20">
                    Welcome, {greetMessage}
                </div>

            </div>
        </>
    );

};

export default HomePage;
