import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { useTheme } from '@/components/theme-provider'
import { ThemeToggle } from '@/components/theme-toggle'

export function AuthPage() {
  const [isLogin, setIsLogin] = useState(true)
  const navigate = useNavigate()
  const { theme } = useTheme()
  const logoSrc = theme === "dark" ? "/weaver_logo_dark.svg" : "/weaver_logo_light.svg"

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    // Assume success for now
    navigate('/')
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-background p-4 relative overflow-hidden">
      {/* Decorative background shapes for modern SaaS feel */}
      <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] rounded-full bg-primary/10 blur-3xl pointer-events-none" />
      <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] rounded-full bg-secondary/10 blur-3xl pointer-events-none" />

      <div className="absolute top-4 right-4 z-10">
        <ThemeToggle />
      </div>
      <Card className="w-full max-w-md relative z-10 border-muted shadow-xl bg-card/50 backdrop-blur-sm">
        <CardHeader className="space-y-3 text-center">
          <div className="flex justify-center mb-4">
            <img src={logoSrc} alt="Weaver" className="w-16 h-16" />
          </div>
          <CardTitle className="text-3xl font-bold tracking-tight">Welcome to Weaver</CardTitle>
          <CardDescription className="text-base">
            {isLogin ? "Sign in to your account" : "Create a new account"}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            {!isLogin && (
              <div className="space-y-2">
                <Input placeholder="Full Name" required className="bg-background/80" />
              </div>
            )}
            <div className="space-y-2">
              <Input type="email" placeholder="Email" required className="bg-background/80" />
            </div>
            <div className="space-y-2">
              <Input type="password" placeholder="Password" required className="bg-background/80" />
            </div>
            <Button type="submit" className="w-full h-11 text-base font-medium shadow-md">
              {isLogin ? "Sign In" : "Sign Up"}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="justify-center border-t border-muted/50 p-6 mt-2">
          <Button variant="link" onClick={() => setIsLogin(!isLogin)} className="text-muted-foreground hover:text-primary transition-colors">
            {isLogin ? "Don't have an account? Sign up" : "Already have an account? Sign in"}
          </Button>
        </CardFooter>
      </Card>
    </div>
  )
}
