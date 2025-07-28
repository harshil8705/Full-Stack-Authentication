import { useState } from "react";
import LoginPage from "./LoginPage";
import SignupPage from "./SignupPage";

const AuthBox = () => {

    const [signUpPage, setSignUpPage] = useState(true);

    return (
        <>
            <div className="h-[90vh] w-[100vh] backdrop-blur-2xl bg-white/30 p-6 rounded-lg flex flex-col gap-6 overflow-hidden">
                <div className="flex justify-center gap-3">
                    <div>
                        <button
                            onClick={() => setSignUpPage(true)}
                            className={`bg-gradient-to-br from-blue-500 to-violet-600 px-3 py-1 rounded-lg hover:from-blue-700 hover:to-violet-800 text-white text-xl font-sans
                            ${signUpPage ? 'border-2 border-white' : ''}`}>
                            Signup Page
                        </button>
                    </div>
                    <div>
                        <button
                            onClick={() => setSignUpPage(false)}
                            className={`bg-gradient-to-br from-blue-500 to-violet-600 px-3 py-1 rounded-lg hover:from-blue-700 hover:to-violet-800 text-white text-xl font-sans
                            ${!signUpPage ? 'border-2 border-white' : ''}`}>
                            Login Page
                        </button>
                    </div>
                </div>
                <div className="flex-1 overflow-y-auto flex justify-center items-start">
                    {signUpPage ? <SignupPage /> : <LoginPage />}
                </div>
            </div>
        </>
    )

}

export default AuthBox;