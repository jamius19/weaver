import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { ThemeProvider } from '@/components/theme-provider'
import { AppLayout } from '@/components/layout/app-layout'
import { Dashboard } from '@/pages/Dashboard'
import { BoardView } from '@/pages/BoardView'
import { AuthPage } from '@/pages/AuthPage'

export default function App() {
  return (
    <ThemeProvider defaultTheme="system" storageKey="weaver-ui-theme">
      <BrowserRouter>
        <Routes>
          <Route path="/auth" element={<AuthPage />} />
          <Route path="/" element={<AppLayout />}>
            <Route index element={<Dashboard />} />
            <Route path="board/:boardId" element={<BoardView />} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  )
}
