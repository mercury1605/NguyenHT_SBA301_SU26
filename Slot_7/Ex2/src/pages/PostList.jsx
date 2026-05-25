// src/pages/PostList.jsx
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import {
  Container,
  Row,
  Col,
  Card,
  Badge,
  Form,
  InputGroup,
  Button,
} from "react-bootstrap";
import Spinner from 'react-bootstrap/Spinner';


function PostList() {
  const navigate = useNavigate();
  const [search, setSearch] = useSearchParams();
  const [activeCategory, setActiveCategory] = useState("Tất cả");
  const keyString = search.get('key') || ""
  const [posts, setPosts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState("")
  const currentPage = Number(search.get('page')) || 1
  const pageSize = 2

  const setQuery = ({ key, page }) => {
    const nextParams = {}
    if (key) {
      nextParams.key = key
    }
    if (page) {
      nextParams.page = page
    }
    setSearch(nextParams)
  }


  const updateSearch = (key) => {
    const nextKey = typeof key === 'string' ? key.trim() : ''
    setQuery({ key: nextKey || undefined, page: 1 })
  }


  const updateActiveCategory = (cat) => {
    setActiveCategory(cat)
    setQuery({ key: keyString || undefined, page: 1 })
  }

  useEffect(() => {
    const controller = new AbortController();
    const fetchPost = async () => {
      const baseUrl = import.meta.env.VITE_API_BASE_URL
      try {
        setError("")
        const response = await fetch(`${baseUrl}/posts`, {
          signal: controller.signal,
        })
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json()
        setPosts(data)
      } catch (error) {
        if (error.name !== "AbortError") {
          console.error('Fetch error:', error);
          setError("Không thể tải dữ liệu. Vui lòng thử lại sau.")
        }
      } finally {
        setLoading(false)
      }
    }
    fetchPost()
    return () => controller.abort();
  }, [])

  // Lấy danh sách category không trùng
  const categories = ["Tất cả", ...new Set(posts.map((p) => p.category))];

  // Lọc theo tìm kiếm và category
  const filtered = posts.filter((post) => {
    const matchSearch = post.title.toLowerCase().includes(keyString.toLowerCase());
    const matchCat =
      activeCategory === "Tất cả" || post.category === activeCategory;
    return matchSearch && matchCat;
  });

  const totalPages = Math.max(1, Math.ceil(filtered.length / pageSize))
  const safePage = Math.min(Math.max(currentPage, 1), totalPages)
  const startIndex = (safePage - 1) * pageSize
  const endIndex = safePage * pageSize

  const paginatedPosts = filtered.slice(startIndex, endIndex)

  const updatePage = (nextPage) => {
    const page = Math.min(Math.max(nextPage, 1), totalPages)
    setQuery({ key: keyString || undefined, page })
  }


  return (
    <Container className="py-4">
      <h2 className="mb-4">📚 Danh sách bài viết</h2>

      {/* Thanh tìm kiếm */}
      <InputGroup className="mb-3">
        <InputGroup.Text>🔍</InputGroup.Text>
        <Form.Control
          value={keyString}
          onChange={(e) => updateSearch(e.target.value)}
          placeholder="Tìm kiếm bài viết..."
        />
        {keyString && (
          <Button variant="outline-secondary" onClick={() => updateSearch("")}>
            × Xóa
          </Button>
        )}
      </InputGroup>

      {/* Bộ lọc category */}
      <div className="mb-4 d-flex gap-2 flex-wrap">
        {categories.map((cat) => (
          <Button
            key={cat}
            variant={activeCategory === cat ? "primary" : "outline-primary"}
            size="sm"
            onClick={() => updateActiveCategory(cat)}
          >
            {cat}
          </Button>
        ))}
      </div>

      <div className="d-flex justify-content-between align-items-center mb-3">
        <Button
          variant="outline-secondary"
          size="sm"
          onClick={() => updatePage(safePage - 1)}
          disabled={safePage <= 1}
        >
          ← Prev
        </Button>
        <small className="text-muted">
          Trang {safePage} / {totalPages}
        </small>
        <Button
          variant="outline-secondary"
          size="sm"
          onClick={() => updatePage(safePage + 1)}
          disabled={safePage >= totalPages}
        >
          Next →
        </Button>
      </div>

      {/* Kết quả */}
      {loading && <Spinner />}
      {error && !loading && (
        <p className="text-danger text-center py-4">{error}</p>
      )}
      {!loading && !error && filtered.length === 0 ? (
        <p className="text-muted text-center py-5">
          Không tìm thấy bài viết nào.
        </p>
      ) : (
        <Row>
          {paginatedPosts.map((post) => (
            <Col md={6} lg={4} key={post.id} className="mb-4">
              <Card
                className="h-100 shadow-sm"
                style={{ cursor: "pointer" }}
                onClick={() => navigate(`/posts/${post.id}`)}
              >
                <Card.Body>
                  <div className="d-flex justify-content-between mb-2">
                    <Badge bg="primary">{post.category}</Badge>
                    <small className="text-muted">{post.date}</small>
                  </div>
                  <Card.Title>{post.title}</Card.Title>
                  <Card.Text className="text-muted small">
                    {post.body.substring(0, 70)}...
                  </Card.Text>
                  {!!post.tags?.length && (
                    <div className="d-flex flex-wrap gap-1 mt-2">
                      {post.tags.map((tag) => (
                        <Badge key={tag} bg="secondary" className="fw-normal">
                          #{tag}
                        </Badge>
                      ))}
                    </div>
                  )}
                </Card.Body>
                <Card.Footer className="text-muted small">
                  ✍️ {post.author}
                </Card.Footer>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
  );
}

export default PostList;
