import 'bootstrap/dist/css/bootstrap.min.css';
import { banners } from './data/banner';
import BannerCarousel from './components/BannerCarousel';
import NavBar from './components/Navbar';
import Orchids from './components/Orchids';
export default function App() {
  return (
    <>
      <NavBar />

      <BannerCarousel banners={banners} />

      <Orchids />
    </>
  )
}
