import SignupPage from "./SignupPage";

const AuthBox = () => {

    return (
        <>
            <div className="h-[90vh] w-3/4 backdrop-blur-2xl bg-white/30 p-6 rounded-lg flex flex-col gap-6 overflow-hidden">
                <div className="flex justify-center gap-3">
                    <div>
                        <button className="bg-gradient-to-br from-blue-500 to-violet-600 px-3 py-1 rounded-lg hover:from-blue-700 hover:to-violet-800 text-white text-xl font-sans">
                            Signup Page
                        </button>
                    </div>
                    <div>
                        <button className="bg-gradient-to-br from-blue-500 to-violet-600 px-3 py-1 rounded-lg hover:from-blue-700 hover:to-violet-800 text-white text-xl font-sans">
                            Login Page
                        </button>
                    </div>
                </div>
                <div className="flex-1 overflow-y-auto flex justify-center items-start">
                    <SignupPage />
                </div>
            </div>
        </>
    )

}

export default AuthBox;