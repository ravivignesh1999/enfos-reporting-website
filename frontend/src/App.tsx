import { BrowserRouter, Routes, Route } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import ReportDetailPage from "./pages/ReportDetailPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/reports/:reportId" element={<ReportDetailPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
