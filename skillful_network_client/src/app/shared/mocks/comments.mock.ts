
export interface IComment {
    id: number;
    user: string;
    text: string;
    votes: number;
    dateOfComment: Date;
}

export let MOCK_COMMENTS: IComment[] = [
    {
        id: 1,
        user: "Huet Mathilde",
        text:"C'est une publication trés intéressante",
        votes: 3,
        dateOfComment: new Date("2020-06-16") ,
    },
    {
        id: 2,
        user: "Clara Lacroix",
        text: 'Je vous souhaite une bonne chance dans vos démarches',
        votes: 5,
        dateOfComment: new Date("2020-07-01") ,
    },
    {
        id: 3,
        user: "Bénoit Anaîs",
        text: "Merci pour cette belle initiative, à partager",
        votes:6,
        dateOfComment: new Date("2020-06-11") ,
    },
];