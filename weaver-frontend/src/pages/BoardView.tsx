import { useParams } from 'react-router-dom'
import { DragDropContext, Droppable, Draggable, type DropResult } from '@hello-pangea/dnd'
import { useKanbanStore } from '@/store/kanbanStore'
import { Card, CardHeader, CardTitle } from '@/components/ui/card'

export function BoardView() {
  const { boardId } = useParams()
  const { stages, issues, moveIssue } = useKanbanStore()

  // Filter stages by current board ID (dummy data boardId = 1)
  const boardStages = stages.filter(s => s.boardId === Number(boardId || 1))

  const onDragEnd = (result: DropResult) => {
    const { destination, source, draggableId } = result
    if (!destination) return
    if (destination.droppableId === source.droppableId && destination.index === source.index) return

    moveIssue(draggableId, destination.droppableId, destination.index)
  }

  return (
    <div className="h-full flex flex-col animate-in fade-in duration-300">
      <div className="p-6 pb-2">
        <h1 className="text-2xl font-bold tracking-tight">Kanban Board</h1>
      </div>
      
      <DragDropContext onDragEnd={onDragEnd}>
        <div className="flex-1 overflow-x-auto p-6 pt-2">
          <div className="flex h-full items-start gap-6">
            {boardStages.map(stage => {
              const stageIssues = issues.filter(i => i.stageId === stage.id)

              return (
                <div key={stage.id} className="flex-shrink-0 w-80 bg-secondary/50 rounded-xl border flex flex-col max-h-full">
                  <div className="p-4 font-semibold flex items-center justify-between border-b bg-secondary/30 rounded-t-xl">
                    <span className="text-[15px]">{stage.title}</span>
                    <span className="text-xs bg-background/80 shadow-sm px-2.5 py-1 rounded-full text-muted-foreground font-medium">
                      {stageIssues.length}
                    </span>
                  </div>
                  
                  <Droppable droppableId={stage.id}>
                    {(provided, snapshot) => (
                      <div
                        {...provided.droppableProps}
                        ref={provided.innerRef}
                        className={`flex-1 overflow-y-auto p-3 space-y-3 transition-colors ${
                          snapshot.isDraggingOver ? 'bg-secondary/80' : ''
                        }`}
                      >
                        {stageIssues.map((issue, index) => (
                          <Draggable key={issue.id} draggableId={issue.id} index={index}>
                            {(provided, snapshot) => (
                              <div
                                ref={provided.innerRef}
                                {...provided.draggableProps}
                                {...provided.dragHandleProps}
                                style={{
                                  ...provided.draggableProps.style,
                                }}
                              >
                                <Card className={`shadow-sm bg-card cursor-grab active:cursor-grabbing border-muted-foreground/10 ${snapshot.isDragging ? 'shadow-xl ring-2 ring-primary rotate-2 scale-105' : 'hover:shadow-md hover:border-primary/30'} transition-all duration-200`}>
                                  <CardHeader className="p-4">
                                    <CardTitle className="text-[15px] leading-snug font-medium text-foreground/90">{issue.title}</CardTitle>
                                  </CardHeader>
                                </Card>
                              </div>
                            )}
                          </Draggable>
                        ))}
                        {provided.placeholder}
                      </div>
                    )}
                  </Droppable>
                </div>
              )
            })}
          </div>
        </div>
      </DragDropContext>
    </div>
  )
}
