import { useState } from 'react'
import { Link, Navigate } from 'react-router-dom'
import { Container, Card, Form, Button, Alert } from 'react-bootstrap'
import { useAuth } from '../context/AuthContext'

export default function LoginPage() {
  const { token, login } = useAuth()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  if (token) return <Navigate to="/" replace />

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true); setError('')
    try { await login(email, password) }
    catch (err) { setError(err.response?.data?.message ?? 'Login failed.') }
    finally { setLoading(false) }
  }

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight:'80vh' }}>
      <Card style={{ width:420 }} className="shadow">
        <Card.Body className="p-4">
          <h3 className="text-center mb-4">Log In</h3>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" value={email} onChange={e => setEmail(e.target.value)} required />
            </Form.Group>
            <Form.Group className="mb-4">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" value={password} onChange={e => setPassword(e.target.value)} required />
            </Form.Group>
            <Button type="submit" className="w-100" disabled={loading}>
              {loading ? 'Logging in...' : 'Log In'}
            </Button>
          </Form>
          <hr />
          <p className="text-center mb-0">Don't have an account? <Link to="/signup">Sign up now</Link></p>
        </Card.Body>
      </Card>
    </Container>
  )
}
