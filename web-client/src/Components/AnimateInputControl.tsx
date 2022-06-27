

export interface AnimateInputControlProps {
    labelName: string;
}

export const AnimateInputControl = ({labelName}: AnimateInputControlProps) => {

    return (
        <div className="relative mx-4">
            <input type="text" required className="px-2 py-1 w-full border border-solid outline-none rounded-sm text-2xl "/>
            <span className="absolute left-0 px-2 py-1 text-xl">{labelName}</span>
        </div>
    );
}