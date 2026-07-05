import { BrowserRouter, Routes, Route, useParams } from 'react-router-dom';
import { OrchidProvider } from './context/OrchidContext';
import AppNavBar from './components/NavBar';
import HomePage from './pages/HomePage';
import AddOrchidPage from './pages/AddOrchidPage';
import EditOrchidPage from './pages/EditOrchidPage';

function EditOrchidRoute() {
  const { id } = useParams();
  return <EditOrchidPage key={id} />;
}

function App() {
  return (
    <OrchidProvider>
      <BrowserRouter>
        <AppNavBar />
        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/add" element={<AddOrchidPage />} />
            <Route path="/edit/:id" element={<EditOrchidRoute />} />
          </Routes>
        </div>
      </BrowserRouter>
    </OrchidProvider>
  );
}

export default App;