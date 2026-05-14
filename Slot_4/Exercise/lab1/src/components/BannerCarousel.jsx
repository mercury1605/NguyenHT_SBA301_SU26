import { Carousel } from 'react-bootstrap';
const BannerCarousel = ({ banners }) => {
    return (
        <Carousel fade interval={1212} pause="hover">
            {banners.map((banner) => (
                <Carousel.Item key={banner.id}>
                    <img
                        className="d-block w-100"
                        src={banner.url}
                        alt={banner.title}
                        style={{ height: '450px', objectFit: 'cover' }}
                    />
                    <Carousel.Caption style={{ background: 'rgba(0,0,0,0.3)', borderRadius: '10px' }}>
                        <h3>{banner.title}</h3>
                        <p>{banner.description}</p>
                    </Carousel.Caption>
                </Carousel.Item>
            ))}
        </Carousel>
    )
}

export default BannerCarousel