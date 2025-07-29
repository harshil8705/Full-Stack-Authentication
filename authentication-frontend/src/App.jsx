import './App.css'
import AuthBox from './AuthBox'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import SignupPage from './SignupPage'
import HomePage from './HomePage'

function App() {

  return (
    <>
      <Router>
        <div className='h-screen w-full bg-linear-to-br from-cyan-300 to-fuchsia-600 via-blue-500 flex justify-center items-center'>
          <Routes>
            <Route path="/" element={<AuthBox />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path="/home" element={<HomePage />} />
          </Routes>
        </div>
      </Router>
    </>
  )
}

export default App
