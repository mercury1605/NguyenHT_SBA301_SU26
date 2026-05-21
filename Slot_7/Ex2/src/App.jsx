import "bootstrap/dist/css/bootstrap.min.css";
// src/App.jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import AppNavbar from "./components/AppNavbar";
import Home from "./pages/Home";
import PostList from "./pages/PostList";
import PostDetail from "./pages/PostDetail";
import About from "./pages/About";
import NotFound from "./pages/NotFound";
import { Registration } from "./pages/Registration";

function App() {
  return (
    <BrowserRouter>
      {/* Navbar luôn hiển thị ở mọi trang */}
      <AppNavbar />

      {/* Định nghĩa các route */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/posts" element={<PostList />} />
        <Route path="/posts/:id" element={<PostDetail />} />
        <Route path="/about" element={<About />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
