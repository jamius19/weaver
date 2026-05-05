import { Link } from "react-router-dom"
import { useKanbanStore } from "@/store/kanbanStore"
import { Card, CardHeader, CardTitle, CardDescription } from "@/components/ui/card"
import { LayoutDashboard } from "lucide-react"

export function Dashboard() {
  const { boards } = useKanbanStore()

  // Provide some default dummy boards if backend is not hooked up
  const displayBoards = boards.length > 0 ? boards : [
    { id: 1, title: 'Engineering Project', description: 'Main tracking board for the engineering team' },
    { id: 2, title: 'Marketing Campaign', description: 'Q3 deliverables and asset tracking' },
  ]

  return (
    <div className="p-8 max-w-6xl mx-auto space-y-8 animate-in fade-in duration-500">
      <div>
        <h1 className="text-3xl font-bold flex items-center gap-3">
          <LayoutDashboard className="w-8 h-8 text-primary" />
          Your Boards
        </h1>
        <p className="text-muted-foreground mt-2 text-lg">Select a board to view its kanban stages.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {displayBoards.map((board) => (
          <Link key={board.id} to={`/board/${board.id}`}>
            <Card className="group hover:border-primary/50 hover:bg-accent/50 transition-all cursor-pointer h-full shadow-sm hover:shadow-md">
              <CardHeader>
                <CardTitle className="group-hover:text-primary transition-colors">{board.title}</CardTitle>
                <CardDescription className="text-sm">{board.description}</CardDescription>
              </CardHeader>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  )
}
