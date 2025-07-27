import axios from "axios";
import { useState } from "react";

const SignupPage = () => {

    const [formData, setFormData] = useState({
        username: "",
        email: "",
        mobileNo: "",
        password: "",
        adminKey: ""
    });

    const [otp, setOtp] = useState("");
    const [showOtpField, setShowOtpField] = useState(false);
    const [isOtpSent, setIsOtpSent] = useState(false);

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        setError("");

        if (!isOtpSent) {

            try {

                const response = await axios.post('http://localhost:9090/api/auth/signup', formData);
                setMessage(response.data);
                setShowOtpField(true);
                setIsOtpSent(true);

            } catch (err) {

                if (err.response && err.respnse.data) {
                    setError(err.response.data.message || "Signup failed")
                } else {
                    setError("Server Not Responding");
                }

            }

        } else {

            try {

                const response = await axios.post('http://localhost:9090/api/auth/verify-user', {
                    email: formData.email,
                    otpToVerify: otp
                });

                setMessage(response.data);
                setFormData({
                    username: "",
                    email: "",
                    mobileNo: "",
                    password: "",
                    adminKey: ""
                });

                setOtp("");
                setShowOtpField(false);
                setIsOtpSent(false);

            } catch (err) {

                if (err.response && err.response.data) {
                    setError(err.response.data.message || "OTP Verification Failed");
                } else {
                    setError("Server Not Responding");
                }

            }

        }

    };

    return(
        <>
            <div className="bg-white w-full rounded-lg p-8 shadow-md">
                <h2 className="text-2xl font-semibold text-center mb-6 text-gray-800">Signup</h2>

                {message && <div className="mb-4 text-green-600 font-medium">{message}</div>}
                {error && <div className="mb-4 text-red-600 font-medium">{error}</div>}

                <form onSubmit={handleSubmit} className="space-y-4">

                    {!isOtpSent && (
                        <>
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
                                type="email"
                                name="email"
                                placeholder="Email"
                                value={formData.email}
                                onChange={handleChange}
                                className="w-full p-2 border border-gray-300 rounded" 
                                required
                            />

                            <input 
                                type="text"
                                name="mobileNo"
                                placeholder="Mobile Number"
                                value={formData.mobileNo}
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

                            <input
                                type="text"
                                name="adminKey"
                                placeholder="Admin Key (Optional)"
                                value={formData.adminKey}
                                onChange={handleChange}
                                className="w-full p-2 border border-gray-300 rounded"
                            />
                        </>
                    )}

                    {showOtpField && (
                        <input 
                            type="text"
                            name="otp"
                            placeholder="Enter OTP"
                            value={otp}
                            onChange={(e) => setOtp(e.target.value)}
                            className="w-full p-2 border border-gray-300 rounded"
                            required
                        />
                    )}

                    <button 
                        type="submit" 
                        className="w-full bg-gradient-to-br from-blue-500 to-violet-600 hover:from-blue-700 hover:to-violet-800 text-white py-2 rounded">
                            {isOtpSent ? 'Submit OTP' : 'Sign Up'}
                    </button>
                </form>
            </div>
        </>
    )

}

export default SignupPage;