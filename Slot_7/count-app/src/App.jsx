import { useState } from "react";
import Button from "react-bootstrap/Button";
import Stack from "react-bootstrap/Stack";

function App() {
  const [count, setCount] = useState(0);

  const handleDownClick = () => {
    if (count <= 0) return
    setCount(count - 1)
  }

  const handleUpCLick = () => {
    setCount(count + 1)
  }

  const handleResetClick = () => {
    setCount(0)
  }

  return (
    <div
      className="min-vh-100 d-flex align-items-center justify-content-center"
      style={{
        background:
          "radial-gradient(circle at top, rgba(255, 208, 122, 0.35), transparent 55%), linear-gradient(160deg, #faf4ee 0%, #eef2f7 100%)",
        padding: "2.5rem 1.5rem",
        fontFamily: '"Playfair Display", "Times New Roman", serif',
      }}
    >
      <div
        className="shadow-lg"
        style={{
          width: "min(92vw, 520px)",
          borderRadius: "28px",
          background: "#ffffff",
          border: "1px solid rgba(24, 38, 69, 0.08)",
          overflow: "hidden",
        }}
      >
        <div
          style={{
            padding: "2.75rem 2.75rem 1.75rem",
            background: "linear-gradient(120deg, #1f2a44 0%, #2f3f5f 100%)",
            color: "#f7f2ec",
          }}
        >
          <div
            style={{
              textTransform: "uppercase",
              letterSpacing: "0.24rem",
              fontSize: "0.75rem",
              color: "rgba(247, 242, 236, 0.8)",
              marginBottom: "0.75rem",
            }}
          >
            Count Studio
          </div>
          <div
            className="fw-semibold"
            style={{
              fontSize: "3.8rem",
              letterSpacing: "0.12rem",
            }}
          >
            {count}
          </div>
          <div style={{ fontSize: "1rem", color: "rgba(247, 242, 236, 0.78)" }}>
            Tap a control to shift the value
          </div>
        </div>
        <Stack gap={3} className="text-center" style={{ padding: "2rem 2.75rem" }}>
          <div
            className="text-uppercase"
            style={{
              fontSize: "0.85rem",
              letterSpacing: "0.18rem",
              color: "#5c6475",
            }}
          >
            Control Panel
          </div>
          <Stack direction="horizontal" gap={2} className="justify-content-center">
            <Button variant="outline-secondary" onClick={handleDownClick}>
              Down
            </Button>
            <Button variant="dark" onClick={handleResetClick}>
              Reset
            </Button>
            <Button variant="primary" onClick={handleUpCLick}>
              Up
            </Button>
          </Stack>
        </Stack>
      </div>
    </div>
  );
}

export default App;
