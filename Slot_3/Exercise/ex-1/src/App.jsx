import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Nav, Navbar, Row, Col } from 'react-bootstrap';
import { flowers } from './data/orchidsData';
import { banners } from './data/banner';
import CustomCard from './components/CustomCard';
import BannerCarousel from './components/BannerCarousel';
const App = () => {
  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand href="#home">Single Page Application</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="#home">Home</Nav.Link>
              <Nav.Link href="#link">Link</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <BannerCarousel banners={banners} />

      <Container className="mt-4">
        <Row>
          {
            flowers.map((flower) => (
              <Col md={3} key={flower.id} className="mb-4">
                <CustomCard orchid={flower} />
              </Col>
            ))
          }
        </Row>
      </Container>
    </>
  )
}

export default App