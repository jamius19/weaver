import { Outlet } from "react-router-dom"
import { ThemeToggle } from "@/components/theme-toggle"
import { useTheme } from "@/components/theme-provider"

export function AppLayout() {
  const { theme } = useTheme()
  const logoSrc = theme === "dark" ? "/weaver_logo_dark.svg" : "/weaver_logo_light.svg"

  return (
    <div className="min-h-screen bg-background flex flex-col overflow-hidden">
      <header className="border-b h-14 flex items-center px-4 justify-between bg-card text-card-foreground">
        <div className="flex items-center gap-2">
          <img src={logoSrc} alt="Weaver Logo" className="w-24" />
        </div>
        <div>
          <ThemeToggle />
        </div>
      </header>
      <div className="flex-1 flex overflow-hidden">
        <main className="flex-1 overflow-auto bg-background">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
