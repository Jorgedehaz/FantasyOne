import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import HubLigas from "./pages/HubLigas.jsx";
import LigaDetalle from './pages/LigaDetalle';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/hubligas" element={<HubLigas />} />
                <Route path="/ligas/:id" element={<LigaDetalle />} />

            </Routes>
        </Router>
    );
}

export default App;

